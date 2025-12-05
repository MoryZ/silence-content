package com.old.silence.content.domain.enums.codegen;

/**
 * @author moryzang
 */
public enum BuildTool {
    MAVEN(1, "MAVEN"),
    YARN(2, "YARN"),
    PIP(3, "PIP"),
    ;

    private final Byte value;
    private final String description;

    BuildTool(int value, String description) {
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
