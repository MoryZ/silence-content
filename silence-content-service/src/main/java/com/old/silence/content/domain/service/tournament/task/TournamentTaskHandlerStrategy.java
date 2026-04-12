package com.old.silence.content.domain.service.tournament.task;

import com.old.silence.content.domain.enums.tournament.TournamentTaskType;
import com.old.silence.content.domain.model.tournament.TournamentTask;

/**
 * 赛事任务处理器
 *
 * @author moryzang
 */
public interface TournamentTaskHandlerStrategy {

    /**
     * 处理器支持的任务类型
     *
     * @return 任务类型
     */
    TournamentTaskType getTaskType();

    /**
     * 执行任务
     *
     * @param task 任务
     */
    void execute(TournamentTask task);
}
