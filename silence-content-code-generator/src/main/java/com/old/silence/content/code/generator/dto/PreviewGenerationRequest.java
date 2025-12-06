package com.old.silence.content.code.generator.dto;

import java.math.BigInteger;

public class PreviewGenerationRequest {
    private String tableName;
    private BigInteger databaseId;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public BigInteger getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(BigInteger databaseId) {
        this.databaseId = databaseId;
    }
}
