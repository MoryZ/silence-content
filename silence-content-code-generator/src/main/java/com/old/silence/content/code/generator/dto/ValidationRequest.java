package com.old.silence.content.code.generator.dto;

import com.old.silence.content.code.generator.config.GeneratorConfig;
import com.old.silence.content.code.generator.model.ApiDocument;

/**
 * 验证步骤请求
 *
 * @author moryzang
 */
public class ValidationRequest {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 全局配置
     */
    private GeneratorConfig globalConfig;

    /**
     * 自定义API文档（可选，用于步骤2和步骤3）
     */
    private ApiDocument customApiDoc;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public GeneratorConfig getGlobalConfig() {
        return globalConfig;
    }

    public void setGlobalConfig(GeneratorConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    public ApiDocument getCustomApiDoc() {
        return customApiDoc;
    }

    public void setCustomApiDoc(ApiDocument customApiDoc) {
        this.customApiDoc = customApiDoc;
    }
}
