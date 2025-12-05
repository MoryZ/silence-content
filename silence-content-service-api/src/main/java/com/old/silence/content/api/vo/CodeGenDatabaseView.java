package com.old.silence.content.api.vo;

import java.math.BigInteger;

import org.springframework.data.web.ProjectedPayload;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface CodeGenDatabaseView {

    BigInteger getId();

    String getDatabaseName();

    String getDatabaseUrl();

    String getUsername();

    String getPassword();

}
