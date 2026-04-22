package com.old.silence.content.api.dto;

/**
 * @author ZHAOGUANRUI108
 */
public class ExerciseDetailVO {
    private String type;           // 项目类型 code，如 squat

    private String name;           // 项目名称，如 "深蹲"

    private Double coefficient;    // 得分系数

    private String duration;       // 持续时间（字符串形式）

    private String timeUnit;       // 时间单位，如 SECOND

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }
}
