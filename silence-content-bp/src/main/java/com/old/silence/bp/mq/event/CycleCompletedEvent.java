package com.old.silence.bp.mq.event;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;

public class CycleCompletedEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private BigInteger eventGameId;

    private Integer cycleNumber;

    private Instant occurredAt;

    public CycleCompletedEvent() {
    }

    public CycleCompletedEvent(BigInteger eventGameId, Integer cycleNumber, Instant occurredAt) {
        this.eventGameId = eventGameId;
        this.cycleNumber = cycleNumber;
        this.occurredAt = occurredAt;
    }

    public BigInteger getEventGameId() {
        return eventGameId;
    }

    public void setEventGameId(BigInteger eventGameId) {
        this.eventGameId = eventGameId;
    }

    public Integer getCycleNumber() {
        return cycleNumber;
    }

    public void setCycleNumber(Integer cycleNumber) {
        this.cycleNumber = cycleNumber;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }
}
