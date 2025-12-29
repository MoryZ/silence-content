package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

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
