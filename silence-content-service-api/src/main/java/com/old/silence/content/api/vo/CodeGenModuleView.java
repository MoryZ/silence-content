package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.codegen.ModuleType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface CodeGenModuleView extends AuditableView {

    BigInteger getId();

    String getModuleName();

    String getModulePackageName();


    String getDisplayName();

    String getDescription();

    ModuleType getModuleType();

    Boolean getEnabled();
}
