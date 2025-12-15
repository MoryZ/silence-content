package com.old.silence.content.code.generator.dto;

import com.old.silence.content.code.generator.enums.CodeGenerateToolType;
import com.old.silence.content.domain.enums.codegen.ModuleType;

import java.util.List;

/**
 * @author moryzang
 */
public class CodeGenModuleConfig {
    private String projectPath;
    private String modulePath;
    private String modulePackageName;
    private ModuleType moduleType;
    private CodeGenerateToolType toolType;
    private List<CodeFileSpecConfig> codeFileSpecConfigs;


    public CodeGenModuleConfig() {
    }

    public CodeGenModuleConfig(String projectPath, String modulePath, String modulePackageName,
                               ModuleType moduleType, CodeGenerateToolType toolType, List<CodeFileSpecConfig> codeFileSpecConfigs) {
        this.projectPath = projectPath;
        this.modulePath = modulePath;
        this.modulePackageName = modulePackageName;
        this.moduleType = moduleType;
        this.toolType = toolType;
        this.codeFileSpecConfigs = codeFileSpecConfigs;
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

    public String getModulePackageName() {
        return modulePackageName;
    }

    public void setModulePackageName(String modulePackageName) {
        this.modulePackageName = modulePackageName;
    }

    public ModuleType getModuleType() {
        return moduleType;
    }

    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }

    public CodeGenerateToolType getToolType() {
        return toolType;
    }

    public void setToolType(CodeGenerateToolType toolType) {
        this.toolType = toolType;
    }

    public List<CodeFileSpecConfig> getCodeFileSpecConfigs() {
        return codeFileSpecConfigs;
    }

    public void setCodeFileSpecConfigs(List<CodeFileSpecConfig> codeFileSpecConfigs) {
        this.codeFileSpecConfigs = codeFileSpecConfigs;
    }
}
