package com.old.silence.code.generator.model;

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

    private String fieldName;       // Java字段名
    private String fieldType;       // Java类型
    private Boolean required;       // 是否必填
    private String defaultValue;    // 默认值
    private Boolean addIsPrefix;    // 是否添加is前缀

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

    public Boolean getAddIsPrefix() {
        return addIsPrefix;
    }

    public void setAddIsPrefix(Boolean addIsPrefix) {
        this.addIsPrefix = addIsPrefix;
    }
}
