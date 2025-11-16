package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import com.old.silence.content.domain.enums.PromptFormatType;

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
    private PromptFormatType formatType;
    private String description;
    @NotNull
    private Boolean active;

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public String getFormatContent() {
        return formatContent;
    }

    public void setFormatContent(String formatContent) {
        this.formatContent = formatContent;
    }

    public PromptFormatType getFormatType() {
        return formatType;
    }

    public void setFormatType(PromptFormatType formatType) {
        this.formatType = formatType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}