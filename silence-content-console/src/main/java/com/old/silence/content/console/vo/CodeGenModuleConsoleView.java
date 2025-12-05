package com.old.silence.content.console.vo;

import java.math.BigInteger;

import com.old.silence.content.domain.enums.codegen.ModuleType;

/**
 * @author moryzang
 */
public interface CodeGenModuleConsoleView {

    BigInteger getId();

    String getModuleName();

    String getDisplayName();

    String getDescription();

    ModuleType getModuleType();

    String getBasePackage();

    String getOutDirectory() ;

    Boolean getEnabled();
}
