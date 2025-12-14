package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 代码文件规格创建/编辑命令
 *
 * @author moryzang
 */
public class CodeFileSpecCommand {

    @NotBlank(message = "模板文件名不能为空")
    private String templateName;

    @NotBlank(message = "模块类型不能为空")
    private String moduleType;

    @NotBlank(message = "包名后缀不能为空")
    private String packageSuffix;

    @NotBlank(message = "相对目录不能为空")
    private String relativeDir;

    @NotBlank(message = "文件名后缀不能为空")
    private String fileNameSuffix;

    @NotBlank(message = "文件类型标签不能为空")
    private String fileTypeTag;

    @NotBlank(message = "生成条件不能为空")
    private String generationCondition;

    private String endpointNames;

    private String displayName;

    private String description;

    @NotNull(message = "启用状态不能为空")
    private Boolean enabled;

    public CodeFileSpecCommand() {
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "CodeFileSpecCommand{" +
                "templateName='" + templateName + '\'' +
                ", moduleType='" + moduleType + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
