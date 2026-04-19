package com.old.silence.content.domain.service.tournament.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.content.domain.enums.MarketingEventStatus;
import com.old.silence.content.domain.model.TournamentTask;
import com.old.silence.content.domain.service.EventGameDomainService;
import com.old.silence.content.domain.service.tournament.TournamentTaskDomainService;
import com.old.silence.content.domain.service.view.EventGameOnlyMarketingEventView;

import java.math.BigInteger;

/**
 * 赛事任务调度分发服务
 *
 * @author EX-ZHANGMENGWEI001
 */
@Service
public class TaskDispatcherService {

    private static final Logger log = LoggerFactory.getLogger(TaskDispatcherService.class);

    private static final int MAX_RETRY_COUNT = 3;

    private final TournamentTaskDomainService tournamentTaskDomainService;
    private final EventGameDomainService eventGameDomainService;

    private final TournamentTaskHandlerFactory taskHandlerFactory;

    public TaskDispatcherService(TournamentTaskDomainService tournamentTaskDomainService,
                                 EventGameDomainService eventGameDomainService,
                                 TournamentTaskHandlerFactory taskHandlerFactory) {
        this.tournamentTaskDomainService = tournamentTaskDomainService;
        this.eventGameDomainService = eventGameDomainService;
        this.taskHandlerFactory = taskHandlerFactory;
    }


    private void processSingleTask(TournamentTask tournamentTask) {

        int nextRetryCount = tournamentTask.getRetryCount() == null ? 1 : tournamentTask.getRetryCount() + 1;

        var id = tournamentTask.getId();
        try {
            checkEventGamePublishStatus(tournamentTask);
            updateStatus(id, tournamentTask.getStatus(), TournamentTaskStatus.RUNNING);
            taskHandlerFactory.getHandler(tournamentTask.getTaskType()).execute(tournamentTask);
            updateStatus(id,  TournamentTaskStatus.RUNNING, TournamentTaskStatus.SUCCESS);
        } catch (Exception ex) {
            if (nextRetryCount >= MAX_RETRY_COUNT) {
                tournamentTask.setStatus(TournamentTaskStatus.TERMINAL_FAILED);
            } else {
                tournamentTask.setStatus(TournamentTaskStatus.FAILED);
            }

            tournamentTask.setRetryCount(nextRetryCount);
            tournamentTaskDomainService.update(tournamentTask);
            log.error("Tournament tournamentTask execution failed, id={}, runTraceId={}, tournamentId={}, taskType={}, stageNo={}, segmentNo={}, cycleNo={}, retryCount={}, status={}",
                    id, tournamentTask.getRunTraceId(), tournamentTask.getTournamentId(), tournamentTask.getTaskType(),
                    tournamentTask.getStageNo(), tournamentTask.getSegmentNo(), tournamentTask.getCycleNo(), tournamentTask.getRetryCount(), tournamentTask.getStatus());

        }
    }

    private void updateStatus(BigInteger id, TournamentTaskStatus newStatus ,TournamentTaskStatus oldStatus) {
        tournamentTaskDomainService.updateStatusAsLock(id, newStatus, oldStatus);
    }

    public void retryTerminalFailedTask(BigInteger id) {
        tournamentTaskDomainService.updateStatusAsLock(id, TournamentTaskStatus.PENDING, TournamentTaskStatus.TERMINAL_FAILED);
    }

    public void runTask(BigInteger id) {
        var tournamentTask = tournamentTaskDomainService.findById(id, TournamentTask.class)
                .orElseThrow(ResourceNotFoundException::new);

        checkEventGamePublishStatus(tournamentTask);

        taskHandlerFactory.getHandler(tournamentTask.getTaskType()).execute(tournamentTask);
        updateStatus(id, TournamentTaskStatus.SUCCESS, tournamentTask.getStatus());
    }

    private void checkEventGamePublishStatus(TournamentTask tournamentTask) {
        var marketingEvent = eventGameDomainService.findById(tournamentTask.getEventGameId(), EventGameOnlyMarketingEventView.class)
                .map(EventGameOnlyMarketingEventView::getEvent)
                .orElseThrow(ResourceNotFoundException::new);
        if (!MarketingEventStatus.PUBLISHED.equals(marketingEvent.getStatus())) {
            log.info("活动未上线！eventId:{},eventGameId:{}", marketingEvent.getId(), tournamentTask.getEventGameId());
        }
    }
}
