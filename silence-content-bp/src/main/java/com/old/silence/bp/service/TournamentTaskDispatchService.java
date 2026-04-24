package com.old.silence.bp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mysql.cj.log.LogFactory;
import com.old.silence.bp.vo.TournamentTaskBpView;
import com.old.silence.content.api.TournamentTaskDomainClient;
import com.old.silence.content.api.tournament.tournament.dto.TournamentTaskQuery;
import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;

import java.time.Instant;
import java.util.List;

@Service
public class TournamentTaskDispatchService {


    private static final Logger log = LoggerFactory.getLogger(TournamentTaskDispatchService.class);
    private static final int BATCH_SIZE = 100;
    private final TournamentTaskDomainClient tournamentTaskDomainClient;
    private final CycleSettleTaskService cycleSettleTaskService;

    public TournamentTaskDispatchService(TournamentTaskDomainClient tournamentTaskDomainClient,
                                         CycleSettleTaskService cycleSettleTaskService) {
        this.tournamentTaskDomainClient = tournamentTaskDomainClient;
        this.cycleSettleTaskService = cycleSettleTaskService;
    }

    public void dispatchPendingTasks() {


        var tournamentTaskQuery = new TournamentTaskQuery();
        tournamentTaskQuery.setTriggerTimeEnd(Instant.now());
        tournamentTaskQuery.setStatuses(List.of(TournamentTaskStatus.PENDING, TournamentTaskStatus.FAILED));

        var pageRequest = PageRequest.of(0, BATCH_SIZE);
        List<TournamentTaskBpView> handleTasks = tournamentTaskDomainClient
                .query(tournamentTaskQuery, pageRequest, TournamentTaskBpView.class)
                .getContent();

        for (TournamentTaskBpView tournamentTask : handleTasks) {
            if (TournamentTaskType.CYCLE_SETTLE.equals(tournamentTask.getTaskType())) {
                // 检查前置任务是否已完成
                var prevTaskId = tournamentTask.getDependsOnTaskId();
                if (prevTaskId != null) {
                    var prevTaskCompleted = tournamentTaskDomainClient.findById(prevTaskId, TournamentTaskBpView.class)
                            .filter(task -> TournamentTaskStatus.SUCCESS.equals(task.getStatus()))
                            .isPresent();
                    if (!prevTaskCompleted) {
                        log.info("前置任务未完成, currentTaskId={}, prevTaskId={}", tournamentTask.getId(), prevTaskId);
                        continue;
                    }
                }
                log.info("执行bp任务, taskId={}", tournamentTask.getId());
                cycleSettleTaskService.execute(tournamentTask);
            } else {
                log.info("在domain执行任务, taskId={}", tournamentTask.getId());
                tournamentTaskDomainClient.runTask(tournamentTask.getId());
            }
        }

    }


}
