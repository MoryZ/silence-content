package com.old.silence.content.console.dto;


/**
 * CodeFileSpec查询对象
 */
public class CodeFileSpecConsoleQuery {
    private String templateName;

    private String moduleType;

    private String packageSuffix;

    private String relativeDir;

    private String fileNameSuffix;

    private String fileTypeTag;

    private String generationCondition;

    private String endpointNames;

    private String displayName;

    private Long version;


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

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}