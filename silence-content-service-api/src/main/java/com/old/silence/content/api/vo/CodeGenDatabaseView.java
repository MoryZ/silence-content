package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface CodeGenDatabaseView extends AuditableView {

    BigInteger getId();

    String getDatabaseName();

    String getDatabaseUrl();

    String getUsername();

    String getPassword();

}
