package com.old.silence.content.api.dto;


import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

/**
 * CodeApiDocument查询对象
 */
public class CodeApiDocumentQuery {


    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}