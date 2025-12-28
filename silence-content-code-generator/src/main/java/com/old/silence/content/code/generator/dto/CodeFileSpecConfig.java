package com.old.silence.content.code.generator.dto;

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

    private List<String> endPointNames;

    public CodeFileSpecConfig(String templateName, String moduleType, String packageSuffix, String relativeDir, String fileNameSuffix,
                              String fileTypeTag, String generationCondition, List<String> endPointNames) {
        this.templateName = templateName;
        this.moduleType = moduleType;
        this.packageSuffix = packageSuffix;
        this.relativeDir = relativeDir;
        this.fileNameSuffix = fileNameSuffix;
        this.fileTypeTag = fileTypeTag;
        this.generationCondition = generationCondition;
        this.endPointNames = endPointNames;

    }

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

    public List<String> getEndPointNames() {
        return endPointNames;
    }

    public void setEndPointNames(List<String> endPointNames) {
        this.endPointNames = endPointNames;
    }
}
