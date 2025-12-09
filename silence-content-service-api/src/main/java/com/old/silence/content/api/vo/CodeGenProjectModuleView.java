package com.old.silence.content.api.vo;

import java.math.BigInteger;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface CodeGenProjectModuleView extends AuditableView {

    BigInteger getId();

    BigInteger getProjectId();

    BigInteger getModuleId();

    CodeGenModuleView getCodeGenModule();

}
