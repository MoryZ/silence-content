package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum ProvisionChannel implements DescribedEnumValue<String>{
    MINI_PROGRAM("C001", "小程序"),
    ;

    private final String value;
    private final String description;

    ProvisionChannel(String value, String description) {
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
