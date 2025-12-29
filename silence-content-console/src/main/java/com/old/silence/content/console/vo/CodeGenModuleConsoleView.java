package com.old.silence.content.console.vo;

import com.old.silence.content.domain.enums.codegen.ModuleType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface CodeGenModuleConsoleView extends AuditableView {

    BigInteger getId();

    String getModuleName();

    String getModulePackageName();

    String getDisplayName();

    String getDescription();

    ModuleType getModuleType();

    Boolean getEnabled();
}
