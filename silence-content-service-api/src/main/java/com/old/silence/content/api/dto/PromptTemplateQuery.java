package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
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
    private BigInteger subCategoryId;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String version;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
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