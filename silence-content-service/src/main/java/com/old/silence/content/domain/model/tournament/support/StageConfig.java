package com.old.silence.content.domain.model.tournament.support;

import java.time.Instant;

/**
 * @author moryzang
 */
public class StageConfig {

    private Instant startTime;
    private Instant endTime;

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }
}
