package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum LoginType implements DescribedEnumValue<Byte> {
    MINI_PROGRAM(1, "小程序登录"),
    OFFICIAL_ACCOUNT(2, "公众号登录"),
    APP(3, "APP登录"),

    ;

    private final Byte value;
    private final String description;

    LoginType(int value, String description) {
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