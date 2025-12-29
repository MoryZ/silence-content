package com.old.silence.content.console.vo;


import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;


/**
 * CodeFileSpec视图接口
 */
public interface CodeFileSpecConsoleView extends AuditableView {
    BigInteger getId();

    String getTemplateName();

    String getModuleType();

    String getPackageSuffix();

    String getRelativeDir();

    String getFileNameSuffix();

    String getFileTypeTag();

    String getGenerationCondition();

    String getEndpointNames();

    String getDisplayName();

    String getDescription();

    Long getVersion();

    Boolean getEnabled();

}