package com.old.silence.bp.mq.event;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;

public class SegmentCompletedEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private BigInteger eventGameId;

    private Integer stageNumber;

    private Instant occurredAt;

    public SegmentCompletedEvent() {
    }

    public SegmentCompletedEvent(BigInteger eventGameId, Integer stageNumber, Instant occurredAt) {
        this.eventGameId = eventGameId;
        this.stageNumber = stageNumber;
        this.occurredAt = occurredAt;
    }

    public BigInteger getEventGameId() {
        return eventGameId;
    }

    public void setEventGameId(BigInteger eventGameId) {
        this.eventGameId = eventGameId;
    }

    public Integer getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(Integer stageNumber) {
        this.stageNumber = stageNumber;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }
}
