package com.old.silence.content.domain.service.tournament.task.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.enums.tournament.TaskTypeEnum;
import com.old.silence.content.domain.model.tournament.TournamentTask;
import com.old.silence.content.domain.service.tournament.task.ITournamentTaskHandler;

/**
 * 周期结算任务处理器
 *
 * @author moryzang
 */
@Component
public class CycleSettleTaskHandler implements ITournamentTaskHandler {

    private static final Logger log = LoggerFactory.getLogger(CycleSettleTaskHandler.class);

    @Override
    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.CYCLE_SETTLE;
    }

    @Override
    public void execute(TournamentTask task) {
        // 1. 聚合本周期内所有片的 tournament_score_record（scoreType=SEGMENT）
        // 2. 计算周期得分（如：累加片得分 或 取最高分，按赛事规则）
        // 3. 写入 tournament_score_record（scoreType=CYCLE）
        // 4. 刷新 tournament_participation_record.total_score（累加最新周期得分）
        log.info("CycleSettleTaskHandler executing, taskId={}, tournamentId={}, periodNo={}",
                task.getTaskId(), task.getTournamentId(), task.getPeriodNo());
    }
}
