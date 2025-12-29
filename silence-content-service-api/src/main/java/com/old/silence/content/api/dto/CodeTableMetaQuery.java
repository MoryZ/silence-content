package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

/**
 * CodeTableMeta查询对象
 */
public class CodeTableMetaQuery {

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private String schemaName;

    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
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