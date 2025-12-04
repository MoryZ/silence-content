package com.old.silence.content.code.generator.dto;

import com.old.silence.content.code.generator.config.GeneratorConfig;

/**
 * @author moryzang
 */
public class RequirementGenerationRequest {

    private String requirement;
    private GeneratorConfig config;

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public GeneratorConfig getConfig() {
        return config;
    }

    public void setConfig(GeneratorConfig config) {
        this.config = config;
    }
}
