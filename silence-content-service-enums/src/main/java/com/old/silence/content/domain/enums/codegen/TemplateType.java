package com.old.silence.content.domain.enums.codegen;

import com.old.silence.core.enums.DescribedEnumValue;

/**
 * @author moryzang
 */
public enum TemplateType implements DescribedEnumValue<Byte> {
    API(1, "API模板"),
    CLIENT(2, "CLIENT模板"),
    COMMAND(3, "COMMAND模板"),
    COMMAND_MAPPER(4, "COMMAND MAPPER"),
    CONSOLE_COMMAND(5, "CONSOLE 请求类"),
    CONSOLE_QUERY(6, "CONSOLE 查询类"),
    CONSOLE_RESOURCE(7, "CONSOLE RESOURCE"),
    CONSOLE_VIEW(8, "CONSOLE VIEW"),
    DAO(9, "DAO 模板"),
    ENUM(10, "ENUM 模板"),
    MAPPER(11, "MAPPER"),
    MODEL(12, "MODEL"),
    QUERY(13, "QUERY"),
    QUERY_MAPPER(14, "QUERY MAPPER"),
    REPOSITORY(15, "REPO"),
    REPOSITORY_IMPL(16, "REPO-IMPL"),
    RESOURCE(17, "RESOURCE"),
    SERVICE(18, "SERVICE"),
    SQL(19, "SQL"),
    TYPE_TS(20, "TYPE SCRIPT"),
    VIEW(21, "VIEW "),
    VUE(22, "VUE"),
    ;

    private final Byte value;
    private final String description;

    TemplateType(int value, String description) {
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
