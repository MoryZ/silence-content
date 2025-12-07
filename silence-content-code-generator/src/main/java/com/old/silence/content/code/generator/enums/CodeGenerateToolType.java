package com.old.silence.content.code.generator.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum CodeGenerateToolType implements DescribedEnumValue<Byte> {

    TEMPLATE(1, "模板代码生成"),
    LLM(2, "LLM大模型生成"),
    HYBRID(3, "混合生成"),
    ;

    private final Byte value;
    private final String description;

    CodeGenerateToolType(int value, String description) {
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
