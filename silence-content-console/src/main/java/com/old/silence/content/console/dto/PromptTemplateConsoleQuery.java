package com.old.silence.content.console.dto;


import java.math.BigInteger;

/**
* PromptTemplate查询对象
*/
public class PromptTemplateConsoleQuery {
    private String templateName;

    private String templateCode;

    private BigInteger subCategoryId;
    private String version;

    private Long sortOrder;


    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    public String getTemplateCode() {
        return this.templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }
    public BigInteger getSubCategoryId() {
        return this.subCategoryId;
    }

    public void setSubCategoryId(BigInteger subCategoryId) {
        this.subCategoryId = subCategoryId;
    }
    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public Long getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

}