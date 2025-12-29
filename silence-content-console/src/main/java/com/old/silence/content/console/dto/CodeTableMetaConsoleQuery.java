package com.old.silence.content.console.dto;


/**
 * CodeTableMeta查询对象
 */
public class CodeTableMetaConsoleQuery {

    private String schemaName;

    private String tableName;

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}