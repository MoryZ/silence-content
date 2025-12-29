package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.PromptTemplateType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
 * PromptTemplate视图接口
 */
@ProjectedPayload
public interface PromptTemplateView extends AuditableView {
    BigInteger getId();

    String getTemplateName();

    String getTemplateCode();

    PromptTemplateType getTemplateType();

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