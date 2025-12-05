package com.old.silence.content.domain.enums.codegen;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum ProjectType implements DescribedEnumValue<Byte> {
    MONOMER(1, "单体项目"),
    MUTI(2, "多模块"),
    ;

    private final Byte value;
    private final String description;

    ProjectType(int value, String description) {
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
