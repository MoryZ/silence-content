package com.old.silence.bp.mq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import com.old.silence.bp.mq.event.CycleCompletedEvent;

@Component
public class CycleEventProducer {

    private static final Logger log = LoggerFactory.getLogger(CycleEventProducer.class);

    private final ApplicationEventPublisher publisher;

    public CycleEventProducer(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void send(CycleCompletedEvent event) {
        log.info("send cycle event, eventGameId={}, cycleNumber={}", event.getEventGameId(), event.getCycleNumber());
        publisher.publishEvent(event);
    }
}
