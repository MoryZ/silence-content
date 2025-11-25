package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum LivePlatform implements DescribedEnumValue<Byte> {
    WECHAT_MINI_PROGRAM_LIVE_BROADCAST(1, "微信小程序直播"),
    ;

    private final Byte value;
    private final String description;

    LivePlatform(int value, String description) {
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
