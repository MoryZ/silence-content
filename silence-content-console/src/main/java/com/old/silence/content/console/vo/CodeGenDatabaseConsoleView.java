package com.old.silence.content.console.vo;

import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface CodeGenDatabaseConsoleView extends AuditableView {

    BigInteger getId();

    String getDatabaseName();

    String getDatabaseUrl();

    String getUsername();

    String getPassword();

}
