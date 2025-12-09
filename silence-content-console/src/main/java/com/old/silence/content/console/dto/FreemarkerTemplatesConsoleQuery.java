package com.old.silence.content.console.dto;

import java.math.BigInteger;

import com.old.silence.content.domain.enums.codegen.TemplateType;

/**
 * FreemarkerTemplates查询对象
 */
public class FreemarkerTemplatesConsoleQuery {
    private BigInteger moduleId;
    private String templateName;

    private TemplateType templateType;
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
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}