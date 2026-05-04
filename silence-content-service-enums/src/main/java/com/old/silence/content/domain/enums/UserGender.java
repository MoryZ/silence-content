package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum UserGender implements DescribedEnumValue<String> {
    UNKNOWN("UNKNOWN", "未知"),
    MALE("MALE", "男性"),
    FEMALE("FEMALE", "女性"),
    ;

    private final String value;
    private final String description;

    UserGender(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
