package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum InteractionType implements DescribedEnumValue<Byte>{
    LIKE(1, "点赞"),
    UN_LIKE(2, "取消点赞"),
    PREVIEW(3, "浏览"),
    SHARE(4, "分享"),
    COLLECT(5, "收藏"),
    UN_COLLECT(6, "取消收藏"),

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

    public boolean isStateful() {
        return this == LIKE || this == UN_LIKE || this == COLLECT || this == UN_COLLECT;
    }

    public boolean isStateless() {
        return this == PREVIEW || this == SHARE;
    }

    public ContentInteractionMode toMode() {
        return isStateful() ? ContentInteractionMode.STATEFUL : ContentInteractionMode.STATELESS;
    }

    public InteractionType normalizeStateType() {
        return switch (this) {
            case UN_LIKE -> LIKE;
            case UN_COLLECT -> COLLECT;
            default -> this;
        };
    }

    public boolean isCancelAction() {
        return this == UN_LIKE || this == UN_COLLECT;
    }
}
