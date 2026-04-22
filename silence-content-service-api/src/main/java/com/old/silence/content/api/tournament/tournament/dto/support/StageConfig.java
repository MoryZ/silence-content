package com.old.silence.content.api.tournament.tournament.dto.support;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

/**
 * @author EX-ZHANGMENGWEI001
 */
public class StageConfig {

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
