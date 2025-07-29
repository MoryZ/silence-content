package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author MurrayZhang
 */
public enum LiveStatus implements DescribedEnumValue<Byte> {
    NOT_STARTED(0, "未开始"),
    LIVING(1, "直播中"),
    STOPPED(2, "已结束"),
    PLAYBACK_ING(3, "回访中"),
    INVALID_LIVE(4, "无效直播"),

    ;

    private final Byte value;
    private final String description;

    LiveStatus(int value, String description) {
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
