package com.old.silence.content.console.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.old.silence.content.domain.enums.codegen.ModuleType;

/**
 * @author moryzang
 */
public class CodeGenModuleConsoleCommand {

    @NotBlank
    private String moduleName;

    @NotBlank
    private String displayName;

    private String description;

    @NotNull
    private ModuleType moduleType;

    @NotBlank
    private String basePackage;

    @NotBlank
    private String outDirectory;

    @NotNull
    private Boolean enabled;


    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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

    public ModuleType getModuleType() {
        return moduleType;
    }

    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
