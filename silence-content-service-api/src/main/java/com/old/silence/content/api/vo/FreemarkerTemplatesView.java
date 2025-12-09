package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.codegen.TemplateType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
 * FreemarkerTemplates视图接口
 */
@ProjectedPayload
public interface FreemarkerTemplatesView extends AuditableView {
    BigInteger getId();

    BigInteger getModuleId();

    String getTemplateName();

    TemplateType getTemplateType();

    String getContent();

    String getDescription();


}