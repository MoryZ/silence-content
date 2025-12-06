package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public class CodeGenProjectModuleCommand {

    @NotNull
    private BigInteger projectId;

    @NotNull
    private BigInteger moduleId;

    public BigInteger getProjectId() {
        return projectId;
    }

    public void setProjectId(BigInteger projectId) {
        this.projectId = projectId;
    }

    public BigInteger getModuleId() {
        return moduleId;
    }

    public void setModuleId(BigInteger moduleId) {
        this.moduleId = moduleId;
    }
}
