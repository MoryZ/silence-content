package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum LiveChannel implements DescribedEnumValue<Byte> {
    GOOD_HELPER_MINI_PROGRAM(1, "好助手小程序"),
    MO_MINI_PROGRAM(2, "随身易小程序"),
    ;

    private final Byte value;
    private final String description;

    LiveChannel(int value, String description) {
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
