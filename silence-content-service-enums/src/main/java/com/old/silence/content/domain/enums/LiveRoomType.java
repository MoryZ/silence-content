package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum LiveRoomType implements DescribedEnumValue<Byte> {
    PUSH_THE_FLOW(1, "推流"),
    MOBILE_LIVE_BROADCAST(2, "手机直播"),
    ;

    private final Byte value;
    private final String description;

    LiveRoomType(int value, String description) {
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
