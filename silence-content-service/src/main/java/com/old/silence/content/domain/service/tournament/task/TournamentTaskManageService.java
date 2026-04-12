package com.old.silence.content.domain.service.tournament.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Service;
import com.old.silence.content.api.dto.TournamentTaskQuery;
import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;
import com.old.silence.content.domain.model.tournament.TournamentConfig;
import com.old.silence.content.domain.model.tournament.TournamentTask;
import com.old.silence.content.domain.repository.tournament.TournamentConfigRepository;
import com.old.silence.content.domain.repository.tournament.TournamentTaskRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * 赛事任务管理服务
 */
@Service
public class TournamentTaskManageService {

    private static final Logger log = LoggerFactory.getLogger(TournamentTaskManageService.class);

    private static final int LOOKUP_SIZE = 1;

    private final TournamentTaskRepository tournamentTaskRepository;

    private final TournamentConfigRepository tournamentConfigRepository;

    public TournamentTaskManageService(TournamentTaskRepository tournamentTaskRepository,
                                       TournamentConfigRepository tournamentConfigRepository) {
        this.tournamentTaskRepository = tournamentTaskRepository;
        this.tournamentConfigRepository = tournamentConfigRepository;
    }

    public <T> Page<T> query(TournamentTaskQuery query, Pageable pageable, Class<T> projectionType) {
        Criteria criteria = QueryCriteriaConverter.convert(query, TournamentTask.class);
        return tournamentTaskRepository.findByCriteria(criteria, pageable, projectionType);
    }

    public int initSettlementTasks(BigInteger tournamentId) {
        Optional<TournamentConfig> configOptional = tournamentConfigRepository.findById(tournamentId, TournamentConfig.class);
        if (configOptional.isEmpty()) {
            throw new IllegalArgumentException("Tournament config not found, id=" + tournamentId);
        }
        if (hasAnyTask(tournamentId)) {
            log.info("Skip init tournament tasks, tasks already exist, tournamentId={}", tournamentId);
            return 0;
        }

        TournamentConfig config = configOptional.get();
        BigInteger eventGameId = config.getEventGameId();
        int totalStages = resolveTotalStages(config);
        String runTraceId = UUID.randomUUID().toString().replace("-", "");
        Instant triggerTime = config.getRegistrationEndTime() == null ? Instant.now() : config.getRegistrationEndTime();

        int created = 0;
        for (int stageNo = 1; stageNo <= totalStages; stageNo++) {
            TournamentTask stageTask = buildTask(tournamentId, eventGameId, TournamentTaskType.STAGE_SETTLE, stageNo, stageNo, stageNo,
                triggerTime, runTraceId);
            tournamentTaskRepository.create(stageTask);
            created++;
        }

        log.info("Initialized registration-end tasks, tournamentId={}, eventGameId={}, totalStages={}, createdStageTasks={}, runTraceId={}",
            tournamentId, eventGameId, totalStages, created, runTraceId);
        return created;
    }

    public void retryTerminalFailedTask(BigInteger taskId) {
        TournamentTask task = tournamentTaskRepository.findById(taskId, TournamentTask.class)
                .orElseThrow(() -> new IllegalArgumentException("Tournament task not found, id=" + taskId));
        if (task.getStatus() != TournamentTaskStatus.TERMINAL_FAILED) {
            throw new IllegalArgumentException("Only TERMINAL_FAILED task can be retried, id=" + taskId
                    + ", status=" + task.getStatus());
        }
        task.setStatus(TournamentTaskStatus.PENDING);
        task.setTriggerTime(Instant.now());
        tournamentTaskRepository.update(task);
        log.info("Manual retry accepted, taskId={}, tournamentId={}, taskType={}, runTraceId={}, retryCount={}",
                task.getId(), task.getTournamentId(), task.getTaskType(), task.getRunTraceId(), task.getRetryCount());
    }

    private boolean hasAnyTask(BigInteger tournamentId) {
        Criteria criteria = Criteria.where("tournament_id").is(tournamentId);
        return !tournamentTaskRepository
                .findByCriteria(criteria, PageRequest.of(0, LOOKUP_SIZE), TournamentTask.class)
                .isEmpty();
    }

    private TournamentTask buildTask(BigInteger tournamentId,
                                     BigInteger eventGameId,
                                     TournamentTaskType taskType,
                                     Integer stageNo,
                                     Integer segmentNo,
                                     Integer cycleNo,
                                     Instant triggerTime,
                                     String runTraceId) {
        TournamentTask task = new TournamentTask();
        task.setTournamentId(tournamentId);
        task.setEventGameId(eventGameId);
        task.setTaskType(taskType);
        task.setStageNo(stageNo);
        task.setSegmentNo(segmentNo);
        task.setCycleNo(cycleNo);
        task.setTriggerTime(triggerTime);
        task.setStatus(TournamentTaskStatus.PENDING);
        task.setRetryCount(0);
        task.setRunTraceId(runTraceId);
        return task;
    }

    private int resolveTotalStages(TournamentConfig config) {
        if (config.getAttributes() == null) {
            return 1;
        }
        Object totalStagesObj = config.getAttributes().get("totalStages");
        if (totalStagesObj instanceof Number number) {
            return number.intValue() <= 0 ? 1 : number.intValue();
        }
        if (totalStagesObj instanceof String str) {
            try {
                int value = Integer.parseInt(str);
                return value <= 0 ? 1 : value;
            } catch (NumberFormatException ex) {
                return 1;
            }
        }
        return 1;
    }
}