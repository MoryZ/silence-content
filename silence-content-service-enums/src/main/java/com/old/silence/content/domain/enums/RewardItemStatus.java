package com.old.silence.content.domain.enums;


import com.old.silence.core.enums.EnumValue;

public enum RewardItemStatus implements EnumValue<Byte> {

    ONLINE(1), OFFLINE(2);

    private final Byte value;

    RewardItemStatus(int value) {
        this.value = (byte) value;
    }

    @Override
    public Byte getValue() {
        return value;
    }
}
