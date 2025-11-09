package com.old.silence.content.console.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
/**
* PromptTemplate命令对象
*/
public class PromptTemplateConsoleCommand {
    @NotBlank
    @Size(max = 100)
    private String templateName;
    @NotBlank
    @Size(max = 50)
    private String templateCode;
    private BigInteger subCategoryId;
    @NotBlank
    @Size(max = 65535)
    private String templateContent;
    private String variableDefinitions;
    private String exampleOutput;
    private String difficultySettings;
    private String questionTypes;
    @NotBlank
    @Size(max = 20)
    private String version;
    @NotNull
    private Boolean active;
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
    public String getTemplateContent() {
        return this.templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }
    public String getVariableDefinitions() {
        return this.variableDefinitions;
    }

    public void setVariableDefinitions(String variableDefinitions) {
        this.variableDefinitions = variableDefinitions;
    }
    public String getExampleOutput() {
        return this.exampleOutput;
    }

    public void setExampleOutput(String exampleOutput) {
        this.exampleOutput = exampleOutput;
    }
    public String getDifficultySettings() {
        return this.difficultySettings;
    }

    public void setDifficultySettings(String difficultySettings) {
        this.difficultySettings = difficultySettings;
    }
    public String getQuestionTypes() {
        return this.questionTypes;
    }

    public void setQuestionTypes(String questionTypes) {
        this.questionTypes = questionTypes;
    }
    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    public Long getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }
}