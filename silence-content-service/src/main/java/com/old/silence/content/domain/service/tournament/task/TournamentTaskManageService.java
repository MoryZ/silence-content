package com.old.silence.content.domain.service.tournament.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Service;
import com.old.silence.content.api.tournament.tournament.dto.TournamentTaskQuery;
import com.old.silence.content.api.tournament.tournament.dto.support.CycleStageConfig;
import com.old.silence.content.api.tournament.tournament.dto.support.StageConfig;
import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.content.domain.model.TournamentConfig;
import com.old.silence.content.domain.model.TournamentTask;
import com.old.silence.content.domain.repository.tournament.TournamentConfigRepository;
import com.old.silence.content.domain.repository.tournament.TournamentTaskRepository;
import com.old.silence.content.domain.service.tournament.TournamentStageCalculationService;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * 赛事任务管理服务
 */
@Service
public class TournamentTaskManageService {

    private static final Logger log = LoggerFactory.getLogger(TournamentTaskManageService.class);

    private final TournamentTaskRepository tournamentTaskRepository;

    private final TournamentConfigRepository tournamentConfigRepository;

    private static final List<TournamentTaskType> cycleSegmentStageJobTaskTypes = List.of(TournamentTaskType.STAGE_SETTLE, TournamentTaskType.SEGMENT_SETTLE, TournamentTaskType.CYCLE_SETTLE);

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
        var configOptional = tournamentConfigRepository.findById(tournamentId, TournamentConfig.class);
        if (configOptional.isEmpty()) {
            throw new IllegalArgumentException("Tournament config not found, id=" + tournamentId);
        }
        // 已经有相关任务 TODO 待定 @孟伟
        if (hasAnyTask(tournamentId)) {
            log.info("Skip init tournament tasks, tasks already exist, tournamentId={}", tournamentId);
            return 0;
        }

        TournamentConfig tournamentConfig = configOptional.get();
        BigInteger eventGameId = tournamentConfig.getEventGameId();


        String runTraceId = UUID.randomUUID().toString().replace("-", "");
        // 总场次
        var totalStages = TournamentStageCalculationService.resolveTotalStages(tournamentConfig.getTournamentStartTime(), tournamentConfig.getTournamentEndTime());
        int created = 0;
        var cycleStageConfig = tournamentConfig.getCycleStageConfig();


        // 插入报名结束任务，一个赛事只有一个
        var registrationTournamentTask = buildTask(tournamentId, eventGameId, TournamentTaskType.REGISTRATION_END, null, null, null,
                tournamentConfig.getRegistrationEndTime().plus(Duration.ofMinutes(tournamentConfig.getChallengeTimeoutMinutes())), runTraceId);
        registrationTournamentTask.setDependsOnStatus(TournamentTaskStatus.SUCCESS);

        try {
            tournamentTaskRepository.create(registrationTournamentTask);
            var dependsOnTaskId = registrationTournamentTask.getId();
            // 插入场次，片，周期 任务
            for (int stageNo = 1; stageNo <= totalStages; stageNo++) {
                Instant triggerTime = getJobTriggerTime(tournamentConfig, cycleStageConfig).plus(Duration.ofDays(stageNo));

                var cycleSegmentStageTriple = TournamentStageCalculationService.calculateCycleSegmentStage(triggerTime, tournamentConfig.getTournamentStartTime(), tournamentConfig.getTournamentEndTime());
                for (TournamentTaskType cycleSegmentStageJobTaskType : cycleSegmentStageJobTaskTypes) {
                    TournamentTask tournamentTaskByTaskType = buildTask(tournamentId, eventGameId, cycleSegmentStageJobTaskType , cycleSegmentStageTriple.getLeft(), cycleSegmentStageTriple.getMiddle(), cycleSegmentStageTriple.getRight(),
                            triggerTime, runTraceId);
                    tournamentTaskByTaskType.setDependsOnTaskId(dependsOnTaskId);
                    tournamentTaskByTaskType.setDependsOnStatus(TournamentTaskStatus.SUCCESS);
                    tournamentTaskRepository.create(tournamentTaskByTaskType);
                    dependsOnTaskId = tournamentTaskByTaskType.getId();
                }

                created++;
            }

            // 发放奖励任务
            var issueRewardTournamentTask = buildTask(tournamentId, eventGameId, TournamentTaskType.ISSUE_REWARD, null, null, null,
                    tournamentConfig.getTournamentEndTime(), runTraceId);
            issueRewardTournamentTask.setDependsOnTaskId(dependsOnTaskId);
            issueRewardTournamentTask.setDependsOnStatus(TournamentTaskStatus.SUCCESS);
            tournamentTaskRepository.create(issueRewardTournamentTask);
        } catch (Exception e) {
            log.error("initiate task error:{}", e.getMessage());
        }

        log.info("Initialized registration-end tasks, tournamentId={}, eventGameId={}, totalStages={}, createdStageTasks={}, runTraceId={}",
            tournamentId, eventGameId, totalStages, created, runTraceId);
        return created;
    }

    private static Instant getJobTriggerTime(TournamentConfig tournamentConfig, CycleStageConfig cycleStageConfig) {
        return TournamentStageCalculationService.buildTriggerTime(tournamentConfig.getTournamentStartTime(),
                CollectionUtils.firstElement(cycleStageConfig.getStageConfigs()).map(StageConfig::getEndTime).get(),
                Duration.ofMinutes(tournamentConfig.getChallengeTimeoutMinutes()));
    }


    private boolean hasAnyTask(BigInteger tournamentId) {
        return tournamentTaskRepository.existsByTournamentId(tournamentId);
    }

    private TournamentTask buildTask(BigInteger tournamentId,
                                     BigInteger eventGameId,
                                     TournamentTaskType taskType,
                                     Integer cycleNo,
                                     Integer segmentNo,
                                     Integer stageNo,
                                     Instant triggerTime,
                                     String runTraceId) {
        TournamentTask tournamentTask = new TournamentTask();
        tournamentTask.setTournamentId(tournamentId);
        tournamentTask.setEventGameId(eventGameId);
        tournamentTask.setTaskType(taskType);
        tournamentTask.setStageNo(stageNo);
        tournamentTask.setSegmentNo(segmentNo);
        tournamentTask.setCycleNo(cycleNo);
        tournamentTask.setTriggerTime(triggerTime);
        tournamentTask.setStatus(TournamentTaskStatus.PENDING);
        tournamentTask.setRetryCount(0);
        tournamentTask.setRunTraceId(runTraceId);
        return tournamentTask;
    }


    public int create(TournamentTask tournamentTask) {
        return tournamentTaskRepository.create(tournamentTask);
    }
}
