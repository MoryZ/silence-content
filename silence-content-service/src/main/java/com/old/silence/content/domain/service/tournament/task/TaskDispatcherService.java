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


    public void processSingleTask(BigInteger id) {
        var tournamentTask = tournamentTaskDomainService.findById(id, TournamentTask.class)
                .orElseThrow(ResourceNotFoundException::new);

        int nextRetryCount = tournamentTask.getRetryCount() == null ? 1 : tournamentTask.getRetryCount() + 1;

        try {
            var eventGameStatusMatch = checkEventGamePublishStatus(tournamentTask);
            if (!eventGameStatusMatch) {
                log.info("Event game status not match");
                return ;
            }
            var hasPrevTask = checkPrevTask(tournamentTask);
            if (hasPrevTask) {
                log.info("前置任务未完成");
                return ;
            }
            updateStatus(id, TournamentTaskStatus.RUNNING, tournamentTask.getStatus());
            taskHandlerFactory.getHandler(tournamentTask.getTaskType()).execute(tournamentTask);
            updateStatus(id,   TournamentTaskStatus.SUCCESS, TournamentTaskStatus.RUNNING);
        } catch (Exception ex) {
            if (nextRetryCount >= MAX_RETRY_COUNT) {
                tournamentTask.setStatus(TournamentTaskStatus.TERMINAL_FAILED);
            } else {
                tournamentTask.setStatus(TournamentTaskStatus.FAILED);
            }

            tournamentTask.setRetryCount(nextRetryCount);
            tournamentTaskDomainService.update(tournamentTask);
            log.error("Tournament tournamentTask execution failed, id={}, ex={}", tournamentTask.getId(), ex.getMessage());

        }
    }

    private boolean checkPrevTask(TournamentTask tournamentTask) {
        return tournamentTaskDomainService.findById(tournamentTask.getDependsOnTaskId(), TournamentTask.class)
                .filter(task -> !TournamentTaskStatus.SUCCESS.equals(task.getStatus())).isPresent();
    }

    private void updateStatus(BigInteger id, TournamentTaskStatus newStatus ,TournamentTaskStatus oldStatus) {
        tournamentTaskDomainService.updateStatusAsLock(id, newStatus, oldStatus);
    }

    public void retryTerminalFailedTask(BigInteger id) {
        tournamentTaskDomainService.updateStatusAsLock(id, TournamentTaskStatus.PENDING, TournamentTaskStatus.TERMINAL_FAILED);
    }

    private boolean checkEventGamePublishStatus(TournamentTask tournamentTask) {
        return eventGameDomainService.findById(tournamentTask.getEventGameId(), EventGameOnlyMarketingEventView.class)
                .map(EventGameOnlyMarketingEventView::getEvent)
                .filter(marketingEvent -> MarketingEventStatus.PUBLISHED.equals(marketingEvent.getStatus()))
                .isPresent();
    }
}
