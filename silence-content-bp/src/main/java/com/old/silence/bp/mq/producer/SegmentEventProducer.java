package com.old.silence.bp.mq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import com.old.silence.bp.mq.event.SegmentCompletedEvent;

@Component
public class SegmentEventProducer {

    private static final Logger log = LoggerFactory.getLogger(SegmentEventProducer.class);

    private final ApplicationEventPublisher publisher;

    public SegmentEventProducer(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void send(SegmentCompletedEvent event) {
        log.info("send segment event, eventGameId={}, stageNumber={}", event.getEventGameId(), event.getStageNumber());
        publisher.publishEvent(event);
    }
}
