package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * CodeFileSpec命令对象
 */
public class CodeFileSpecCommand {
    @NotBlank
    @Size(max = 100)
    private String templateName;
    @NotBlank
    @Size(max = 50)
    private String moduleType;

    private String packageSuffix;
    @NotBlank
    @Size(max = 200)
    private String relativeDir;

    private String fileNameSuffix;
    @NotBlank
    @Size(max = 50)
    private String fileTypeTag;
    @NotBlank
    @Size(max = 50)
    private String generationCondition;
    private String endpointNames;
    private String displayName;
    private String description;
    @NotNull
    private Long version;
    @NotNull
    private Boolean enabled;

    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getModuleType() {
        return this.moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getPackageSuffix() {
        return this.packageSuffix;
    }

    public void setPackageSuffix(String packageSuffix) {
        this.packageSuffix = packageSuffix;
    }

    public String getRelativeDir() {
        return this.relativeDir;
    }

    public void setRelativeDir(String relativeDir) {
        this.relativeDir = relativeDir;
    }

    public String getFileNameSuffix() {
        return this.fileNameSuffix;
    }

    public void setFileNameSuffix(String fileNameSuffix) {
        this.fileNameSuffix = fileNameSuffix;
    }

    public String getFileTypeTag() {
        return this.fileTypeTag;
    }

    public void setFileTypeTag(String fileTypeTag) {
        this.fileTypeTag = fileTypeTag;
    }

    public String getGenerationCondition() {
        return this.generationCondition;
    }

    public void setGenerationCondition(String generationCondition) {
        this.generationCondition = generationCondition;
    }

    public String getEndpointNames() {
        return this.endpointNames;
    }

    public void setEndpointNames(String endpointNames) {
        this.endpointNames = endpointNames;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}