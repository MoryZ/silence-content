package com.old.silence.content.domain.service.tournament.task.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.enums.tournament.TaskTypeEnum;
import com.old.silence.content.domain.model.tournament.TournamentTask;
import com.old.silence.content.domain.service.tournament.task.ITournamentTaskHandler;

/**
 * 片结算任务处理器
 *
 * @author moryzang
 */
@Component
public class SegmentSettleTaskHandler implements ITournamentTaskHandler {

    private static final Logger log = LoggerFactory.getLogger(SegmentSettleTaskHandler.class);

    @Override
    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.SEGMENT_SETTLE;
    }

    @Override
    public void execute(TournamentTask task) {
        // 1. 聚合本片内所有场次的 tournament_score_record（scoreType=STAGE）
        // 2. 计算片得分（如：取最大场次分 或 累加场次分，按赛事规则）
        // 3. 写入 tournament_score_record（scoreType=SEGMENT）
        log.info("SegmentSettleTaskHandler executing, taskId={}, tournamentId={}, periodNo={}",
                task.getTaskId(), task.getTournamentId(), task.getPeriodNo());
    }
}
