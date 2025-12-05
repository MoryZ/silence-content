package com.old.silence.content.code.generator.dto;

import com.old.silence.content.code.generator.model.ApiDocument;

/**
 * 步骤2：API文档响应
 *
 * @author moryzang
 */
public class Step2ApiDocResponse {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 生成的API文档
     */
    private ApiDocument apiDocument;

    /**
     * 是否自定义API文档
     */
    private boolean customApi;

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

    public ApiDocument getApiDocument() {
        return apiDocument;
    }

    public void setApiDocument(ApiDocument apiDocument) {
        this.apiDocument = apiDocument;
    }

    public boolean isCustomApi() {
        return customApi;
    }

    public void setCustomApi(boolean customApi) {
        this.customApi = customApi;
    }
}
