package com.old.silence.content.api.vo;

import java.math.BigInteger;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.codegen.ModuleType;
import com.old.silence.data.commons.domain.AuditableView;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface CodeGenModuleView extends AuditableView {

    BigInteger getId();

    String getModuleName();

    String getDisplayName();

    String getDescription();

    ModuleType getModuleType();

    Boolean getEnabled();
}
