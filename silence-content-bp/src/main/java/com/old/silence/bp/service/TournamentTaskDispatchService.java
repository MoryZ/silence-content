package com.old.silence.bp.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import com.old.silence.bp.vo.TournamentTaskBpView;
import com.old.silence.content.api.TournamentTaskDomainClient;
import com.old.silence.content.api.tournament.tournament.dto.TournamentTaskQuery;
import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;

import java.time.Instant;
import java.util.List;

@Service
public class TournamentTaskDispatchService {

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
                cycleSettleTaskService.execute(tournamentTask);
            }
            tournamentTaskDomainClient.runTask(tournamentTask.getId());
        }

    }


}
