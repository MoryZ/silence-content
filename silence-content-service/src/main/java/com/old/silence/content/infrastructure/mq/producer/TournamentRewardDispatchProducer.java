package com.old.silence.content.infrastructure.mq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.service.tournament.task.event.TournamentRewardDispatchRequestedEvent;
import com.old.silence.content.infrastructure.mq.producer.message.TournamentRewardDispatchMessage;
import com.old.silence.json.JacksonMapper;

/**
 * 赛事发奖消息生产者。
 */
@Component
public class TournamentRewardDispatchProducer {

    private static final Logger log = LoggerFactory.getLogger(TournamentRewardDispatchProducer.class);
    private static final String MESSAGE_SOURCE = "tournament-task-dispatcher";

    private final SilenceContentProducer silenceContentProducer;
    private final JacksonMapper jacksonMapper;
    private final String topic;
    private final String tag;

    public TournamentRewardDispatchProducer(SilenceContentProducer silenceContentProducer,
                                           JacksonMapper jacksonMapper,
                                           @Value("${silence.content.mq.tournament-reward.topic:TOURNAMENT_REWARD_TOPIC}") String topic,
                                           @Value("${silence.content.mq.tournament-reward.tag:DISPATCH}") String tag) {
        this.silenceContentProducer = silenceContentProducer;
        this.jacksonMapper = jacksonMapper;
        this.topic = topic;
        this.tag = tag;
    }

    public void send(TournamentRewardDispatchRequestedEvent event) {
        TournamentRewardDispatchMessage message = new TournamentRewardDispatchMessage(
                event.tournamentId(),
                event.eventGameId(),
                event.runTraceId(),
                event.occurredAt(),
                MESSAGE_SOURCE);
        String payload = jacksonMapper.toJson(message);
        silenceContentProducer.sendWithTag(topic, tag, payload);

        log.info("reward dispatch mq message sent, topic={}, tag={}, tournamentId={}, eventGameId={}, runTraceId={}",
                topic, tag, event.tournamentId(), event.eventGameId(), event.runTraceId());
    }
}