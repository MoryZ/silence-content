package com.old.silence.content.domain.model.tournament.support;

/**
 * @author moryzang
 */
public class ExerciseScoreCoefficient {

    private String code;
    private String name;
    private String scoreRate;
    private String pickOption;
    private int priority;
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

    public String getScoreRate() {
        return scoreRate;
    }

    public void setScoreRate(String scoreRate) {
        this.scoreRate = scoreRate;
    }

    public String getPickOption() {
        return pickOption;
    }

    public void setPickOption(String pickOption) {
        this.pickOption = pickOption;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getExerciseItemImageUrl() {
        return exerciseItemImageUrl;
    }

    public void setExerciseItemImageUrl(String exerciseItemImageUrl) {
        this.exerciseItemImageUrl = exerciseItemImageUrl;
    }
}
