package com.old.silence.content.code.generator.dto;

import com.old.silence.content.code.generator.config.GeneratorConfig;
import com.old.silence.content.code.generator.model.ApiDocument;

import java.util.List;
import java.util.Map;

public class PreviewGenerationRequest {
    private GeneratorConfig config;
    private List<String> tableNames;
    private Map<String, ApiDocument> customApiDocs;

    public GeneratorConfig getConfig() { return config; }
    public void setConfig(GeneratorConfig config) { this.config = config; }

    public List<String> getTableNames() { return tableNames; }
    public void setTableNames(List<String> tableNames) { this.tableNames = tableNames; }

    public Map<String, ApiDocument> getCustomApiDocs() { return customApiDocs; }
    public void setCustomApiDocs(Map<String, ApiDocument> customApiDocs) { this.customApiDocs = customApiDocs; }
}
