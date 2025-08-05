package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum OrderStatus implements DescribedEnumValue<Byte> {
    ORDERED(1, "已下单"),
    PAID(2, "已支付"),
    RECEIVED(3, "已收货"),
    ;

    private final Byte value;
    private final String description;

    OrderStatus(int value, String description) {
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
