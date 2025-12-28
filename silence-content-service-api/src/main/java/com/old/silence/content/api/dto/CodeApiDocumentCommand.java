package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* CodeApiDocument命令对象
*/
public class CodeApiDocumentCommand {
    @NotBlank
    @Size(max = 100)
    private String tableName;
    private String apiName;
    @NotBlank
    @Size(max = 65535)
    private String detail;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getApiName() {
        return this.apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }
    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}