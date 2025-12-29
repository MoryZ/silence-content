package com.old.silence.content.console.dto;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public class CodeGenProjectModuleConsoleQuery {

    private BigInteger projectId;

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
