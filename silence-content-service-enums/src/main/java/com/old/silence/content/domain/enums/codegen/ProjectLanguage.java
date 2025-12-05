package com.old.silence.content.domain.enums.codegen;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum ProjectLanguage implements DescribedEnumValue<Byte> {
    JAVA(1, "JAVA"),
    JAVASCRIPT(2, "JAVASCRIPT"),
    TYPESCRIPT(3, "TYPESCRIPT"),
    PYTHON(4, "PYTHON"),
    ;

    private final Byte value;
    private final String description;

    ProjectLanguage(int value, String description) {
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
