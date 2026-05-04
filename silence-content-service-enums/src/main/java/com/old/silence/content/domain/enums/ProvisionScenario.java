package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum ProvisionScenario implements DescribedEnumValue<String>{
    USER_LOGIN("S001", "用户登录"),
    ;

    private final String value;
    private final String description;

    ProvisionScenario(String value, String description) {
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
