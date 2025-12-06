package com.old.silence.content.console.dto;

import com.old.silence.content.domain.enums.codegen.ModuleType;

/**
 * @author moryzang
 */
public class CodeGenModuleConsoleQuery {

    private String moduleName;

    private String displayName;

    private ModuleType moduleType;

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
