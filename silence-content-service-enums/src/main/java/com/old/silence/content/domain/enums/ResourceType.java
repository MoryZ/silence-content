package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum ResourceType implements DescribedEnumValue<Byte>{
    CONTENT(1, "内容"),
    POETRY(2, "诗歌"),
    ;

    private final Byte value;
    private final String description;

    ResourceType(int value, String description) {
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
