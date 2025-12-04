package com.old.silence.content.code.generator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author moryzang
 */
public class IndexInfo {

    private String indexName;
    private boolean unique;
    private List<String> columnNames = new ArrayList<>();
    private String indexType;

    // 构造函数
    public IndexInfo() {}

    public IndexInfo(String indexName, boolean unique) {
        this.indexName = indexName;
        this.unique = unique;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public void addColumnName(String columnName) {
        this.columnNames.add(columnName);
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }
}
