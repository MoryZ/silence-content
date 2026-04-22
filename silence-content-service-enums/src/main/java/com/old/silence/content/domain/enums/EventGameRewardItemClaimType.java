package com.old.silence.content.domain.enums;


import com.old.silence.core.enums.DescribedEnumValue;

public enum EventGameRewardItemClaimType implements DescribedEnumValue<Byte> {

    AUTOMATIC(1, "自动领取"),
    HAND_OPERATION(2, "手动领取")
    ;

    private final Byte value;

    private final String description;

    EventGameRewardItemClaimType(int value, String description) {
        this.value = (byte) value;
        this.description = description;
    }

    @Override
    public Byte getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
