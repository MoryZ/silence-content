package com.old.silence.content.console.enums;


import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author EX-ZHANGMENGWEI001
 */
public enum TimeCheckType implements DescribedEnumValue<Byte> {
    OVERLAP(1, "重叠"),
    CONTINUOUS(2, "连续"),

    ;
    private final Byte value;

    private final String description;

    TimeCheckType(int value, String description) {
        this.value = (byte) value;
        this.description = description;
    }

    @Override
    public Byte getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

}
