package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.time.Instant;

/**
* CodeTableMeta查询对象
*/
public class CodeTableMetaQuery {
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String tableName;


    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}