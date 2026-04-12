package com.old.silence.content.domain.service.tournament.task.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.old.silence.content.infrastructure.mq.producer.TournamentRewardDispatchProducer;

/**
 * 奖励发放事件监听器（事件驱动）
 */
@Component
public class TournamentRewardDispatchEventListener {

    private static final Logger log = LoggerFactory.getLogger(TournamentRewardDispatchEventListener.class);

    private final TournamentRewardDispatchProducer tournamentRewardDispatchProducer;

    public TournamentRewardDispatchEventListener(TournamentRewardDispatchProducer tournamentRewardDispatchProducer) {
        this.tournamentRewardDispatchProducer = tournamentRewardDispatchProducer;
    }

    @EventListener
    public void onRewardDispatchRequested(TournamentRewardDispatchRequestedEvent event) {
        try {
            tournamentRewardDispatchProducer.send(event);
        } catch (Exception ex) {
            log.error("reward-dispatch mq send failed, tournamentId={}, eventGameId={}, runTraceId={}",
                    event.tournamentId(), event.eventGameId(), event.runTraceId(), ex);
            throw ex;
        }
    }
}