package com.old.silence.content.code.generator.dto;

import com.old.silence.content.code.generator.enums.CodeGenerateToolType;

/**
 * @author moryzang
 */
public class CodeGenModuleConfig {
    private String projectPath;
    private String modulePath;
    private String basePackage;
    private String outDirectory;
    private CodeGenerateToolType toolType;

    public CodeGenModuleConfig() {
    }

    public CodeGenModuleConfig(String projectPath, String modulePath, String basePackage,
                               String outDirectory, CodeGenerateToolType toolType) {
        this.projectPath = projectPath;
        this.modulePath = modulePath;
        this.basePackage = basePackage;
        this.outDirectory = outDirectory;
        this.toolType = toolType;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getModulePath() {
        return modulePath;
    }

    public void setModulePath(String modulePath) {
        this.modulePath = modulePath;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getOutDirectory() {
        return outDirectory;
    }

    public void setOutDirectory(String outDirectory) {
        this.outDirectory = outDirectory;
    }

    public CodeGenerateToolType getToolType() {
        return toolType;
    }

    public void setToolType(CodeGenerateToolType toolType) {
        this.toolType = toolType;
    }
}
