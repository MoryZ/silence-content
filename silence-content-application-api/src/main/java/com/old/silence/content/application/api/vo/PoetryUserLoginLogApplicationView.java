package com.old.silence.content.application.api.vo;

import java.math.BigInteger;
import java.time.Instant;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.LoginType;
import com.old.silence.data.commons.domain.AuditableView;

/**
* PoetryUserLoginLog视图接口
*/
@ProjectedPayload
public interface PoetryUserLoginLogApplicationView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    String getOpenid();

    LoginType getLoginType();

    String getIpAddress();

    String getSessionKey();

    Boolean getLoginStatus();

    Instant getLoginTime();


}