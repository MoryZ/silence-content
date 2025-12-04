package com.old.silence.content.code.generator.dto;

import com.old.silence.content.code.generator.config.GeneratorConfig;
import com.old.silence.content.code.generator.model.ApiDocument;

import java.util.List;
import java.util.Map;

/**
 * 批量生成请求
 *
 * @author moryzang
 */
public class BatchGenerationRequest {

    /**
     * 表名列表，为空则生成所有表
     */
    private List<String> tableNames;

    /**
     * 生成配置
     */
    private GeneratorConfig config;

    /**
     * 自定义API文档映射 (tableName -> ApiDocument)
     * 用于支持复杂接口场景，如果提供则使用自定义的ApiDocument而不是从表结构自动生成
     */
    private Map<String, ApiDocument> customApiDocs;

    public List<String> getTableNames() {
        return tableNames;
    }

    public void setTableNames(List<String> tableNames) {
        this.tableNames = tableNames;
    }

    public GeneratorConfig getConfig() {
        return config;
    }

    public void setConfig(GeneratorConfig config) {
        this.config = config;
    }

    public Map<String, ApiDocument> getCustomApiDocs() {
        return customApiDocs;
    }

    public void setCustomApiDocs(Map<String, ApiDocument> customApiDocs) {
        this.customApiDocs = customApiDocs;
    }
}
