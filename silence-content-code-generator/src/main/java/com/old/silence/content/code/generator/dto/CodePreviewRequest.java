package com.old.silence.content.code.generator.dto;

import com.old.silence.content.code.generator.config.GeneratorConfig;
import com.old.silence.content.code.generator.model.ApiDocument;

/**
 * 代码预览请求
 *
 * @author moryzang
 */
public class CodePreviewRequest {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 生成配置
     */
    private GeneratorConfig config;

    /**
     * 自定义API文档（可选，用于复杂接口场景）
     */
    private ApiDocument customApiDoc;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public GeneratorConfig getConfig() {
        return config;
    }

    public void setConfig(GeneratorConfig config) {
        this.config = config;
    }

    public ApiDocument getCustomApiDoc() {
        return customApiDoc;
    }

    public void setCustomApiDoc(ApiDocument customApiDoc) {
        this.customApiDoc = customApiDoc;
    }
}
