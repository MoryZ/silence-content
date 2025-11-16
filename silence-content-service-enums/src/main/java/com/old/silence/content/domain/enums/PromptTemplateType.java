package com.old.silence.content.domain.enums;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum PromptTemplateType implements DescribedEnumValue<Byte> {
    LEARNING_CONTENT(1, "学习内容"),
    TEST_TOPIC(2, "测试题目"),
    ;

    private final Byte value;
    private final String description;

    PromptTemplateType(int value, String description) {
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
