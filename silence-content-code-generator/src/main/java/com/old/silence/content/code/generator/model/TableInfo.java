package com.old.silence.content.code.generator.model;

import java.util.List;

/**
 * @author moryzang
 */
public class TableInfo {
    private String tableName;
    private String schemaName;
    private String comment;
    private TableDetail detail;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TableDetail getDetail() {
        return detail;
    }

    public void setDetail(TableDetail detail) {
        this.detail = detail;
    }


    public List<ColumnInfo> getColumnInfos() {
        return detail.getColumnInfos();
    }

    public void setColumnInfos(List<ColumnInfo> columnInfos) {
        this.getDetail().setColumnInfos(columnInfos);
    }

    public List<String> getPrimaryKeys() {
        return detail.getPrimaryKeys();
    }

    public void setPrimaryKeys(List<String> primaryKeys) {
        this.detail.setPrimaryKeys(primaryKeys);
    }

    public List<ForeignKey> getForeignKeys() {
        return detail.getForeignKeys();
    }

    public void setForeignKeys(List<ForeignKey> foreignKeys) {
        this.detail.setForeignKeys(foreignKeys);
    }

    public List<IndexInfo> getIndexes() {
        return detail.getIndexes();
    }

    public void setIndexes(List<IndexInfo> indexes) {
        this.detail.setIndexes(indexes);
    }
}