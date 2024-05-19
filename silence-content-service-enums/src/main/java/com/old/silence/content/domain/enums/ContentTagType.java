package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author MurrayZhang
 */
public enum ContentTagType implements DescribedEnumValue<Byte>{
    TECHNOLOGY(1, "科技"),
    LITERATURE(2, "文学"),
    MATH(3, "数学"),

    ;

    private final Byte value;
    private final String description;

    ContentTagType(int value, String description) {
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
