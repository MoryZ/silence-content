package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum InteractionType implements DescribedEnumValue<Byte>{
    SUBSCRIBE(1, "订阅"),
    UN_SUBSCRIBE(2, "取消订阅"),
    LIKE(3, "点赞"),
    UN_LIKE(4, "取消点赞"),
    PREVIEW(5, "浏览"),
    FORWARD(6, "转发"),

    ;

    private final Byte value;
    private final String description;

    InteractionType(int value, String description) {
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
