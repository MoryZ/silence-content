package com.old.silence.content.domain.enums.codegen;

/**
 * @author moryzang
 */
public enum ProjectType {
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
