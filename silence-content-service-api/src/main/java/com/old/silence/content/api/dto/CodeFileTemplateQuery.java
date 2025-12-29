package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.codegen.TemplateType;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

/**
 * CodeFileTemplate查询对象
 */
public class CodeFileTemplateQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger moduleId;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String templateName;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TemplateType templateType;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
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