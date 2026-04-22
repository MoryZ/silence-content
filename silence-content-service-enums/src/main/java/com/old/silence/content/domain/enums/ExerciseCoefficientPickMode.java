package com.old.silence.content.domain.enums;


import com.old.silence.core.enums.DescribedEnumValue;

/**
 * 运动项任务模式
 *
 * @author EX-GUOWEI869
 */
public enum ExerciseCoefficientPickMode implements DescribedEnumValue<Byte> {

    RANDOM(1, "随机"),
    SEQUENCE(2, "顺序"),

    ;

    private final Byte value;

    private final String description;

    ExerciseCoefficientPickMode(int value, String description) {
        this.value = (byte) value;
        this.description = description;
    }

    @Override
    public Byte getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
