package com.old.silence.content.api;

import org.springframework.web.bind.annotation.PostMapping;

/**
 * TournamentTask服务接口（内部调度用）
 */
interface TournamentTaskService {

    /**
     * 调度并执行待处理任务
     *
     * @return 本次调度的任务数量
     */
    @PostMapping(value = "/tournament-tasks/dispatch")
    int dispatchPendingTasks();
}
