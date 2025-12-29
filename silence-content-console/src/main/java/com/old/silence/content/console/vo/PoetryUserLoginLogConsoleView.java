package com.old.silence.content.console.vo;

import com.old.silence.content.domain.enums.LoginType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.time.Instant;

/**
 * PoetryUserLoginLog视图接口
 */
public interface PoetryUserLoginLogConsoleView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    String getOpenid();

    LoginType getLoginType();

    String getIpAddress();

    String getSessionKey();

    Boolean getLoginStatus();

    Instant getLoginTime();

}