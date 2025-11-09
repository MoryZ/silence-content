package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
/**
* PromptCommonFormat命令对象
*/
public class PromptCommonFormatCommand {
    @NotBlank
    @Size(max = 50)
    private String formatName;
    @NotBlank
    @Size(max = 65535)
    private String formatContent;
    private String description;
    @NotNull
    private Boolean active;

    public String getFormatName() {
        return this.formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }
    public String getFormatContent() {
        return this.formatContent;
    }

    public void setFormatContent(String formatContent) {
        this.formatContent = formatContent;
    }
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}