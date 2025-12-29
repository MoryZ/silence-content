package com.old.silence.content.code.generator.model;

import org.apache.commons.lang3.StringUtils;

/**
 * @author moryzang
 */
public class ColumnInfo {

    private String originalName;
    private String type;
    private Long length;
    private Boolean nullable;
    private Boolean autoIncrement;
    private String comment;
    private Boolean isPrimaryKey = false;
    private Boolean isEnum = false;
    private Boolean isIndexColumn = false;

    private String fieldName;       // Java字段名
    private String fieldType;       // Java类型
    private Boolean required;       // 是否必填
    private String defaultValue;    // 默认值

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public Boolean getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(Boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public Boolean getEnum() {
        return isEnum;
    }

    public void setEnum(Boolean anEnum) {
        isEnum = anEnum;
    }

    public Boolean getIndexColumn() {
        return isIndexColumn;
    }

    public void setIndexColumn(Boolean indexColumn) {
        isIndexColumn = indexColumn;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * Derive and set Java field type from the database `type`.
     * Mirrors the generator's type conversion logic.
     */
    public void setFieldType() {
        String javaType;
        if (type == null) {
            javaType = "Object";
        } else {
            String t = type.toLowerCase();
            javaType = switch (t) {
                case "bigint" -> "BigInteger";
                case "int" -> "Long";
                case "smallint" ->
                    // Default to Integer for small/tiny int; enums handled separately
                        "Integer";
                case "tinyint" -> StringUtils.capitalize(fieldName);
                case "decimal", "numeric" -> "BigDecimal";
                case "float" -> "Float";
                case "double", "real" -> "Double";
                case "datetime", "timestamp" -> "Instant";
                case "date" -> "LocalDate";
                case "time" -> "LocalTime";
                case "boolean", "bool", "bit" -> "Boolean";
                case "blob", "binary" -> "byte[]";
                default -> "String";
            };
        }
        this.fieldType = javaType;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

}
