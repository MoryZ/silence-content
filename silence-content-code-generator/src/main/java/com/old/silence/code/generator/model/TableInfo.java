package com.old.silence.code.generator.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author moryzang
 */
public class TableInfo {
    private String tableName;
    private List<ColumnInfo> columns;
    private List<String> primaryKeys;
    private List<ForeignKey> foreignKeys;
    private List<IndexInfo> indexes = new ArrayList<>();
    private String schema;
    private String comment;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnInfo> columns) {
        this.columns = columns;
    }

    public List<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public List<ForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(List<ForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public List<IndexInfo> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<IndexInfo> indexes) {
        this.indexes = indexes;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // 辅助方法
    public void addColumn(ColumnInfo column) {
        this.columns.add(column);
    }

    public void addPrimaryKey(String primaryKey) {
        this.primaryKeys.add(primaryKey);
    }

    public void addForeignKey(ForeignKey foreignKey) {
        this.foreignKeys.add(foreignKey);
    }

    public void addIndex(IndexInfo index) {
        this.indexes.add(index);
    }

    /**
     * 获取普通索引（非唯一索引）
     */
    public List<IndexInfo> getNormalIndexes() {
        List<IndexInfo> normalIndexes = new ArrayList<>();
        for (IndexInfo index : indexes) {
            if (!index.isUnique() && isNotPrimaryKeyIndex(index)) {
                normalIndexes.add(index);
            }
        }
        return normalIndexes;
    }

    /**
     * 获取唯一索引（不包括主键索引）
     */
    public List<IndexInfo> getUniqueIndexes() {
        List<IndexInfo> uniqueIndexes = new ArrayList<>();
        for (IndexInfo index : indexes) {
            if (index.isUnique() && isNotPrimaryKeyIndex(index)) {
                uniqueIndexes.add(index);
            }
        }
        return uniqueIndexes;
    }

    /**
     * 判断是否为索引
     */
    private boolean isNotPrimaryKeyIndex(IndexInfo index) {
        // 主键索引通常以 PRIMARY 命名或者包含所有主键列
        if ("PRIMARY".equalsIgnoreCase(index.getIndexName())) {
            return true;
        }

        // 检查索引列是否与主键列完全匹配
        if (index.getColumnNames().size() == primaryKeys.size()) {
            return new HashSet<>(index.getColumnNames()).containsAll(primaryKeys);
        }

        return false;
    }

    /**
     * 根据列名获取索引信息
     */
    public List<IndexInfo> getIndexesByColumn(String columnName) {
        List<IndexInfo> result = new ArrayList<>();
        for (IndexInfo index : indexes) {
            if (index.getColumnNames().contains(columnName)) {
                result.add(index);
            }
        }
        return result;
    }
}