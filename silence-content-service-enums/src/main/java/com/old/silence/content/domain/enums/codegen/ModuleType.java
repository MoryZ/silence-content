package com.old.silence.content.domain.enums.codegen;

/**
 * @author moryzang
 */
public enum ModuleType {
    ENUM(1, "枚举模块"),
    SERVICE_API(2, "接口定义模块"),
    SERVICE(3, "service服务模块"),
    CONSOLE(4, "console服务模块"),
    ;

    private final Byte value;
    private final String description;

    ModuleType(int value, String description) {
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
