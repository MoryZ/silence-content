package com.old.silence.content.code.generator.dto;

import com.old.silence.content.domain.enums.codegen.ModuleType;

import java.util.List;

/**
 * @author moryzang
 */
public class CodeFileSpecConfig {

    private String templateName;

    private String moduleType;

    private String packageSuffix;

    private String relativeDir;

    private String fileNameSuffix;

    private String fileTypeTag;

    private String generationCondition;

    private List<String> endpointNames;

    private String displayName;

    private String description;


    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getPackageSuffix() {
        return packageSuffix;
    }

    public void setPackageSuffix(String packageSuffix) {
        this.packageSuffix = packageSuffix;
    }

    public String getRelativeDir() {
        return relativeDir;
    }

    public void setRelativeDir(String relativeDir) {
        this.relativeDir = relativeDir;
    }

    public String getFileNameSuffix() {
        return fileNameSuffix;
    }

    public void setFileNameSuffix(String fileNameSuffix) {
        this.fileNameSuffix = fileNameSuffix;
    }

    public String getFileTypeTag() {
        return fileTypeTag;
    }

    public void setFileTypeTag(String fileTypeTag) {
        this.fileTypeTag = fileTypeTag;
    }

    public String getGenerationCondition() {
        return generationCondition;
    }

    public void setGenerationCondition(String generationCondition) {
        this.generationCondition = generationCondition;
    }

    public List<String> getEndpointNames() {
        return endpointNames;
    }

    public void setEndpointNames(List<String> endpointNames) {
        this.endpointNames = endpointNames;
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
}
