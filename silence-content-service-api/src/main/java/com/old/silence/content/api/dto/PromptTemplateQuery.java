package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.PromptTemplateType;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

/**
 * PromptTemplate查询对象
 */
public class PromptTemplateQuery {
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String templateName;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String templateCode;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private PromptTemplateType templateType;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger subCategoryId;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String version;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long sortOrder;


    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public PromptTemplateType getTemplateType() {
        return templateType;
    }

    public void setTemplateType(PromptTemplateType templateType) {
        this.templateType = templateType;
    }

    public BigInteger getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(BigInteger subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }
}