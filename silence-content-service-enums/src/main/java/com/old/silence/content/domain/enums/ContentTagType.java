package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum ContentTagType implements DescribedEnumValue<Byte>{
    NORMAL(1, "分发栏目"),
    SYSTEM(9, "系统标签"),
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
