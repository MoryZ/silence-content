package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum StudyStatus implements DescribedEnumValue<Byte>{
    STUDYING(1, "学习中"),
    COMPLETED(2, "已学完"),
    ;

    private final Byte value;
    private final String description;

    StudyStatus(int value, String description) {
        this.value = (byte)value;
        this.description = description;
    }

    public Byte getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
