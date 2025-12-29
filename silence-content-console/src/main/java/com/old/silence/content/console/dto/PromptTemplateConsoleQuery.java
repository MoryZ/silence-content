package com.old.silence.content.console.dto;


import com.old.silence.content.domain.enums.PromptTemplateType;

import java.math.BigInteger;

/**
 * PromptTemplate查询对象
 */
public class PromptTemplateConsoleQuery {
    private String templateName;

    private String templateCode;

    private PromptTemplateType templateType;

    private BigInteger subCategoryId;

    private String version;

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