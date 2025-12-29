package com.old.silence.content.code.generator.model;

import java.util.List;

/**
 * @author moryzang
 */
public class TableDetail {

    private List<ColumnInfo> columnInfos;
    private List<String> primaryKeys;
    private List<ForeignKey> foreignKeys;
    private List<IndexInfo> indexes;


    public List<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public void setColumnInfos(List<ColumnInfo> columnInfos) {
        this.columnInfos = columnInfos;
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
}
