package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum GradeLevel implements DescribedEnumValue<Byte> {
    PRIMARY(1, "小学"),
    MIDDLE(2, "初中"),
    SENIOR(3, "高中"),
    ;

    private final Byte value;
    private final String description;

    GradeLevel(int value, String description) {
        this.value = (byte) value;
        this.description = description;
    }

    public Byte getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
