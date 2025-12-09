package com.old.silence.content.console.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;

import com.old.silence.content.domain.enums.codegen.TemplateType;

/**
 * FreemarkerTemplates命令对象
 */
public class FreemarkerTemplatesConsoleCommand {
    @NotNull
    private BigInteger moduleId;
    @NotBlank
    @Size(max = 50)
    private String templateName;
    private TemplateType templateType;
    @NotBlank
    @Size(max = 65535)
    private String content;
    private String description;

    public BigInteger getModuleId() {
        return this.moduleId;
    }

    public void setModuleId(BigInteger moduleId) {
        this.moduleId = moduleId;
    }
    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    public TemplateType getTemplateType() {
        return this.templateType;
    }

    public void setTemplateType(TemplateType templateType) {
        this.templateType = templateType;
    }
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
