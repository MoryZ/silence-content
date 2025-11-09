package com.old.silence.content.console.vo;

import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
* PromptTemplate视图接口
*/
public interface PromptTemplateConsoleView extends AuditableView {
    BigInteger getId();

    String getTemplateName();
    String getTemplateCode();
    BigInteger getSubCategoryId();
    String getTemplateContent();
    String getVariableDefinitions();
    String getExampleOutput();
    String getDifficultySettings();
    String getQuestionTypes();
    String getVersion();
    Boolean getActive();
    Long getSortOrder();

}