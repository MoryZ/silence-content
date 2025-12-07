package com.old.silence.content.code.generator.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum CodeGenerateType implements DescribedEnumValue<Byte> {

    SQL(1, "SQL 语句"),
    DATABASE(2, "数据库"),
    API_DOC(3, "API 接口文档"),
    REQUIREMENT(4, "需求文档"),
    ;

    private final Byte value;
    private final String description;

    CodeGenerateType(int value, String description) {
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
