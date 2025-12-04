package com.old.silence.content.code.generator.dto;

import com.old.silence.content.code.generator.config.GeneratorConfig;

/**
 * @author moryzang
 */
public class SQLGenerationRequest {

    private String sql;
    private GeneratorConfig config;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public GeneratorConfig getConfig() {
        return config;
    }

    public void setConfig(GeneratorConfig config) {
        this.config = config;
    }
}
