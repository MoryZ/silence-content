package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.old.silence.content.domain.enums.codegen.BuildTool;
import com.old.silence.content.domain.enums.codegen.ProjectLanguage;
import com.old.silence.content.domain.enums.codegen.ProjectType;

/**
 * @author moryzang
 */
public class CodeGenProjectCommand {

    @NotBlank
    private String projectName;

    @NotNull
    private ProjectType projectType;

    @NotBlank
    private String displayName;

    private String description;

    @NotNull
    private String baseDirectory;

    private String repoUrl;

    @NotNull
    private String owner;

    @NotNull
    private ProjectLanguage language;

    @NotNull
    private BuildTool buildTool;

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public void setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ProjectLanguage getLanguage() {
        return language;
    }

    public void setLanguage(ProjectLanguage language) {
        this.language = language;
    }

    public BuildTool getBuildTool() {
        return buildTool;
    }

    public void setBuildTool(BuildTool buildTool) {
        this.buildTool = buildTool;
    }
}
