package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.LoginType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.time.Instant;

/**
 * PoetryUserLoginLog视图接口
 */
@ProjectedPayload
public interface PoetryUserLoginLogView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    String getOpenid();

    LoginType getLoginType();

    String getIpAddress();

    String getSessionKey();

    Boolean getLoginStatus();

    Instant getLoginTime();


}