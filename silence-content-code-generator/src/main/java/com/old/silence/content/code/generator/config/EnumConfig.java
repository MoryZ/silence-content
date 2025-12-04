package com.old.silence.content.code.generator.config;

import java.util.List;

/**
 * 枚举配置
 * 用于显式指定字段对应的枚举类型
 *
 * @author moryzang
 */
public class EnumConfig {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段名
     */
    private String columnName;

    /**
     * 枚举类名（不含包名）
     * 例如：UserStatusEnum, OrderTypeEnum
     */
    private String enumClassName;

    /**
     * 枚举值列表
     */
    private List<EnumValue> values;

    /**
     * 是否生成枚举类
     * 如果为false，仅使用Integer/String等基础类型
     */
    private Boolean generateEnum = true;

    /**
     * 枚举描述
     */
    private String description;

    public static class EnumValue {
        /**
         * 枚举值的code（数据库中的值）
         */
        private String code;

        /**
         * 枚举值的名称（枚举常量名）
         */
        private String name;

        /**
         * 枚举值的描述
         */
        private String description;

        public EnumValue() {
        }

        public EnumValue(String code, String name, String description) {
            this.code = code;
            this.name = name;
            this.description = description;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getEnumClassName() {
        return enumClassName;
    }

    public void setEnumClassName(String enumClassName) {
        this.enumClassName = enumClassName;
    }

    public List<EnumValue> getValues() {
        return values;
    }

    public void setValues(List<EnumValue> values) {
        this.values = values;
    }

    public Boolean getGenerateEnum() {
        return generateEnum;
    }

    public void setGenerateEnum(Boolean generateEnum) {
        this.generateEnum = generateEnum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
