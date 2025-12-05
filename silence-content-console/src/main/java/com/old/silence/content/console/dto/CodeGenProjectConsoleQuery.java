package com.old.silence.content.console.dto;

import com.old.silence.content.domain.enums.codegen.ProjectType;

/**
 * @author moryzang
 */
public class CodeGenProjectConsoleQuery {

    private String projectName;

    private ProjectType projectType;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }
}
