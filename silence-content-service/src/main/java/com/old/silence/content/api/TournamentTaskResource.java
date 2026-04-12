package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.dto.TournamentTaskQuery;
import com.old.silence.content.domain.service.tournament.task.TaskDispatcherService;
import com.old.silence.content.domain.service.tournament.task.RegistrationEndOrchestrationService;
import com.old.silence.content.domain.service.tournament.task.StageEndOrchestrationService;
import com.old.silence.content.domain.service.tournament.task.TournamentTaskManageService;

import java.math.BigInteger;

/**
 * 赛事任务调度控制器（内部调度用，由 bp 模块 Job 定时触发）
 *
 * @author moryzang
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class TournamentTaskResource implements TournamentTaskService {

    private final TaskDispatcherService taskDispatcherService;

    private final TournamentTaskManageService tournamentTaskManageService;

    private final RegistrationEndOrchestrationService registrationEndOrchestrationService;

    private final StageEndOrchestrationService stageEndOrchestrationService;

    public TournamentTaskResource(TaskDispatcherService taskDispatcherService,
                                  TournamentTaskManageService tournamentTaskManageService,
                                  RegistrationEndOrchestrationService registrationEndOrchestrationService,
                                  StageEndOrchestrationService stageEndOrchestrationService) {
        this.taskDispatcherService = taskDispatcherService;
        this.tournamentTaskManageService = tournamentTaskManageService;
        this.registrationEndOrchestrationService = registrationEndOrchestrationService;
        this.stageEndOrchestrationService = stageEndOrchestrationService;
    }

    @Override
    public <T> Page<T> query(TournamentTaskQuery query, Pageable pageable, Class<T> projectionType) {
        return tournamentTaskManageService.query(query, pageable, projectionType);
    }

    @Override
    public int initSettlementTasks(BigInteger tournamentId) {
        return tournamentTaskManageService.initSettlementTasks(tournamentId);
    }

    @Override
    public void retryTerminalFailedTask(BigInteger taskId) {
        tournamentTaskManageService.retryTerminalFailedTask(taskId);
    }

    @Override
    public int handleRegistrationEnd() {
        return registrationEndOrchestrationService.handleRegistrationEnd();
    }

    @Override
    public int handleStageEnd() {
        return stageEndOrchestrationService.handleStageEnd();
    }

    @Override
    public int dispatchPendingTasks() {
        return taskDispatcherService.dispatchPendingTasks();
    }

    @Override
    public boolean hasUnfinishedTasks(BigInteger tournamentId) {
        return taskDispatcherService.hasUnfinishedTasks(tournamentId);
    }
}
