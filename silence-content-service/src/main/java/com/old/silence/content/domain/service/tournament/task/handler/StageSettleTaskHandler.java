package com.old.silence.content.domain.service.tournament.task.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.enums.tournament.TaskTypeEnum;
import com.old.silence.content.domain.model.tournament.TournamentTask;
import com.old.silence.content.domain.service.tournament.task.ITournamentTaskHandler;

/**
 * 场次结算任务处理器
 *
 * @author moryzang
 */
@Component
public class StageSettleTaskHandler implements ITournamentTaskHandler {

    private static final Logger log = LoggerFactory.getLogger(StageSettleTaskHandler.class);

    @Override
    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.STAGE_SETTLE;
    }

    @Override
    public void execute(TournamentTask task) {
        // 1. 读取 challenge_record
        // 2. 计算本场次最高分
        // 3. 写入 tournament_score_record
        log.info("StageSettleTaskHandler executing, taskId={}, tournamentId={}, periodNo={}",
                task.getTaskId(), task.getTournamentId(), task.getPeriodNo());
    }
}
