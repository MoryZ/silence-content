package com.old.silence.content.console.vo;

import com.old.silence.content.domain.enums.codegen.TemplateType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
 * CodeFileTemplate视图接口
 */
public interface CodeFileTemplateConsoleView extends AuditableView {
    BigInteger getId();

    BigInteger getModuleId();

    String getTemplateName();

    TemplateType getTemplateType();

    String getContent();

    String getDescription();

}
