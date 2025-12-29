package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.old.silence.content.domain.enums.codegen.ModuleType;

/**
 * @author moryzang
 */
public class CodeGenModuleCommand {

    @NotBlank
    private String moduleName;

    private String modulePackageName;

    @NotBlank
    private String displayName;

    private String description;

    @NotNull
    private ModuleType moduleType;

    @NotNull
    private Boolean enabled;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModulePackageName() {
        return modulePackageName;
    }

    public void setModulePackageName(String modulePackageName) {
        this.modulePackageName = modulePackageName;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
