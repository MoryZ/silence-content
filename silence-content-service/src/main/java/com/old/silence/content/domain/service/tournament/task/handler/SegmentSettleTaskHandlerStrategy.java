package com.old.silence.content.domain.service.tournament.task.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import com.old.silence.content.domain.enums.tournament.TournamentTaskType;
import com.old.silence.content.domain.model.TournamentTask;
import com.old.silence.content.domain.service.tournament.task.TournamentTaskHandlerStrategy;


/**
 * 片结算任务处理器
 *
 * @author EX-ZHANGMENGWEI001
 */
@Component
public class SegmentSettleTaskHandlerStrategy implements TournamentTaskHandlerStrategy {

    private static final Logger log = LoggerFactory.getLogger(SegmentSettleTaskHandlerStrategy.class);

    @Override
    public TournamentTaskType getTaskType() {
        return TournamentTaskType.SEGMENT_SETTLE;
    }

    @Override
    public void execute(TournamentTask tournamentTask) {

        log.info("SegmentSettleTaskHandler done, id={}, tournamentId={}, segmentNo={}",
            tournamentTask.getId(), tournamentTask.getTournamentId(), tournamentTask.getSegmentNo());
    }


}
