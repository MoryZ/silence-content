package com.old.silence.content.api.dto;

import java.time.LocalDateTime;

/**
 * 代码文件规格视图 DTO
 * 用于前端展示和查询结果返回
 *
 * @author moryzang
 */
public class CodeFileSpecView {

    private Long id;

    private String templateName;

    private String moduleType;

    private String packageSuffix;

    private String relativeDir;

    private String fileNameSuffix;

    private String fileTypeTag;

    private String generationCondition;

    private String endpointNames;

    private String displayName;

    private String description;

    private Integer version;

    private Boolean enabled;

    private String createdBy;

    private LocalDateTime createdDate;

    private String updatedBy;

    private LocalDateTime updatedDate;

    public CodeFileSpecView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEndpointNames() {
        return endpointNames;
    }

    public void setEndpointNames(String endpointNames) {
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "CodeFileSpecView{" +
                "id=" + id +
                ", templateName='" + templateName + '\'' +
                ", moduleType='" + moduleType + '\'' +
                ", displayName='" + displayName + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
