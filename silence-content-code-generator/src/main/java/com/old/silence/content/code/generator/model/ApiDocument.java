package com.old.silence.content.code.generator.model;

import java.util.Map;

/**
 * @author moryzang
 */
public class ApiDocument {

    private String tableName;
    private TableInfo tableInfo;
    private Map<String, ApiEndpoint> endpoints;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public Map<String, ApiEndpoint> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Map<String, ApiEndpoint> endpoints) {
        this.endpoints = endpoints;
    }

}
