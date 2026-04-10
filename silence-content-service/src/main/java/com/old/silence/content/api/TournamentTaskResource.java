package com.old.silence.content.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.domain.service.tournament.task.TaskDispatcherService;

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

    public TournamentTaskResource(TaskDispatcherService taskDispatcherService) {
        this.taskDispatcherService = taskDispatcherService;
    }

    @Override
    public int dispatchPendingTasks() {
        return taskDispatcherService.dispatchPendingTasks();
    }
}
