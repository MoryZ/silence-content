package com.old.silence.content.code.generator.dto;

import com.old.silence.content.code.generator.model.ColumnInfo;

import java.util.List;

/**
 * 步骤1：表信息响应
 *
 * @author moryzang
 */
public class Step1TableInfoResponse {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 主键列表
     */
    private List<String> primaryKeys;

    /**
     * 列信息列表
     */
    private List<ColumnInfo> columns;

    /**
     * 索引信息
     */
    private List<IndexInfo> indexes;

    /**
     * 外键信息
     */
    private List<ForeignKeyInfo> foreignKeys;

    public static class IndexInfo {
        private String indexName;
        private List<String> columns;
        private boolean unique;

        public IndexInfo() {
        }

        public IndexInfo(String indexName, List<String> columns, boolean unique) {
            this.indexName = indexName;
            this.columns = columns;
            this.unique = unique;
        }

        public String getIndexName() {
            return indexName;
        }

        public void setIndexName(String indexName) {
            this.indexName = indexName;
        }

        public List<String> getColumns() {
            return columns;
        }

        public void setColumns(List<String> columns) {
            this.columns = columns;
        }

        public boolean isUnique() {
            return unique;
        }

        public void setUnique(boolean unique) {
            this.unique = unique;
        }
    }

    public static class ForeignKeyInfo {
        private String columnName;
        private String referencedTable;
        private String referencedColumn;

        public ForeignKeyInfo() {
        }

        public ForeignKeyInfo(String columnName, String referencedTable, String referencedColumn) {
            this.columnName = columnName;
            this.referencedTable = referencedTable;
            this.referencedColumn = referencedColumn;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getReferencedTable() {
            return referencedTable;
        }

        public void setReferencedTable(String referencedTable) {
            this.referencedTable = referencedTable;
        }

        public String getReferencedColumn() {
            return referencedColumn;
        }

        public void setReferencedColumn(String referencedColumn) {
            this.referencedColumn = referencedColumn;
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnInfo> columns) {
        this.columns = columns;
    }

    public List<IndexInfo> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<IndexInfo> indexes) {
        this.indexes = indexes;
    }

    public List<ForeignKeyInfo> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(List<ForeignKeyInfo> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }
}
