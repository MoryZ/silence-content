package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;


/**
* CodeFileSpec查询对象
*/
public class CodeFileSpecQuery {
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String templateName;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String moduleType;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String packageSuffix;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String relativeDir;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String fileNameSuffix;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String fileTypeTag;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String generationCondition;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String endpointNames;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String displayName;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
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