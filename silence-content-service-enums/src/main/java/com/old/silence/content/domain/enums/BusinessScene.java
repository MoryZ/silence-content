package com.old.silence.content.domain.enums;


import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author YANGWENCHANG983
 */
public enum BusinessScene implements DescribedEnumValue<String> {
    SELF("SELF", "健康险自营"),
    PK("PK", "AI PK 赛制"),
    // MO(1, "随身易")
    ;

    private final String value;

    private final String description;

    BusinessScene(String value, String description) {
        this.value =  value;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getValue() {
        return value;
    }
}
