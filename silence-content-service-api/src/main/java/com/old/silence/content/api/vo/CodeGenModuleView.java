package com.old.silence.content.api.vo;

import java.math.BigInteger;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.codegen.ModuleType;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface CodeGenModuleView {

    BigInteger getId();

    String getModuleName();

    String getDisplayName();

    String getDescription();

    ModuleType getModuleType();

    String getBasePackage();

    String getOutDirectory() ;

    Boolean getEnabled();
}
