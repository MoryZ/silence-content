package com.old.silence.content.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigInteger;

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

    /**
     * 查询赛事是否存在未完成任务（PENDING/RUNNING）
     *
     * @param tournamentId 赛事ID
     * @return true=存在未完成任务
     */
    @GetMapping(value = "/tournament-tasks/unfinished")
    boolean hasUnfinishedTasks(@RequestParam("tournamentId") BigInteger tournamentId);
}
