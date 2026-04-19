package com.old.silence.content.api.tournament.tournament.dto.support;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author EX-ZHANGMENGWEI001
 */
public class ExerciseScoreCoefficient {

    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotNull
    private Integer duration;
    @NotBlank
    private String unit;
    @NotNull
    private BigDecimal scoreRate;
    @NotNull
    private Integer priority;
    private String exerciseItemImageUrl;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getScoreRate() {
        return scoreRate;
    }

    public void setScoreRate(BigDecimal scoreRate) {
        this.scoreRate = scoreRate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getExerciseItemImageUrl() {
        return exerciseItemImageUrl;
    }

    public void setExerciseItemImageUrl(String exerciseItemImageUrl) {
        this.exerciseItemImageUrl = exerciseItemImageUrl;
    }
}
