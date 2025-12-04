package com.old.silence.content.code.generator.dto;

import com.old.silence.content.code.generator.config.GeneratorConfig;
import com.old.silence.content.code.generator.model.ApiDocument;

/**
 * @author moryzang
 */
public class ApiDocGenerationRequest {

    private ApiDocument apiDoc;
    private GeneratorConfig config;

    public ApiDocument getApiDoc() {
        return apiDoc;
    }

    public void setApiDoc(ApiDocument apiDoc) {
        this.apiDoc = apiDoc;
    }

    public GeneratorConfig getConfig() {
        return config;
    }

    public void setConfig(GeneratorConfig config) {
        this.config = config;
    }
}
