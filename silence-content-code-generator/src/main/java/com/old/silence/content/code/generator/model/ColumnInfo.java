package com.old.silence.content.code.generator.model;

/**
 * @author moryzang
 */
public class ColumnInfo {

    private String originalName;
    private String type;
    private Integer length;
    private Boolean nullable;
    private Boolean autoIncrement;
    private String comment;
    private Boolean isPrimaryKey = false;

    private Boolean isEnum = false;
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

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
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
            if (t.contains("bigint")) {
                javaType = "BigInteger";
            } else if (t.contains("int")) {
                javaType = "Long";
            } else if (t.contains("smallint")) {
                // Default to Integer for small/tiny int; enums handled separately
                javaType = "Integer";
            } else if (t.contains("tinyint")) {
              javaType = "EnumValue";
            } else if (t.contains("decimal") || t.contains("numeric")) {
                javaType = "BigDecimal";
            } else if (t.contains("float")) {
                javaType = "Float";
            } else if (t.contains("double") || t.contains("real")) {
                javaType = "Double";
            } else if (t.contains("varchar") || t.contains("char") ||
                    t.contains("text") || t.contains("enum") ||
                    t.contains("set") || t.contains("json")) {
                javaType = "String";
            } else if (t.contains("datetime") || t.contains("timestamp")) {
                javaType = "Instant";
            } else if (t.contains("date")) {
                javaType = "LocalDate";
            } else if (t.contains("time")) {
                javaType = "LocalTime";
            } else if (t.contains("boolean") || t.contains("bool") || t.contains("bit")) {
                javaType = "Boolean";
            } else if (t.contains("blob") || t.contains("binary")) {
                javaType = "byte[]";
            } else {
                javaType = "String";
            }
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
