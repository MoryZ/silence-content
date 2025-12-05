package com.old.silence.content.console.vo;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface CodeGenDatabaseConsoleView {

    BigInteger getId();

    String getDatabaseName();

    String getDatabaseUrl();

    String getUsername();

    String getPassword();

}
