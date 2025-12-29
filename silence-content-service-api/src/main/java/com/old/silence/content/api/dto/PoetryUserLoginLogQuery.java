package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;
import java.time.Instant;

/**
 * PoetryUserLoginLog查询对象
 */
public class PoetryUserLoginLogQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger userId;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String openid;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long loginType;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String ipAddress;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String sessionKey;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Boolean loginStatus;
    @RelationalQueryProperty(name = "loginTime", type = Part.Type.GREATER_THAN_EQUAL)
    private Instant loginTimeStart;

    @RelationalQueryProperty(name = "loginTime", type = Part.Type.LESS_THAN_EQUAL)
    private Instant loginTimeEnd;


    public BigInteger getUserId() {
        return this.userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Long getLoginType() {
        return this.loginType;
    }

    public void setLoginType(Long loginType) {
        this.loginType = loginType;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSessionKey() {
        return this.sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Boolean getLoginStatus() {
        return this.loginStatus;
    }

    public void setLoginStatus(Boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Instant getLoginTimeStart() {
        return this.loginTimeStart;
    }

    public void setLoginTimeStart(Instant loginTimeStart) {
        this.loginTimeStart = loginTimeStart;
    }

    public Instant getLoginTimeEnd() {
        return this.loginTimeEnd;
    }

    public void setLoginTimeEnd(Instant loginTimeEnd) {
        this.loginTimeEnd = loginTimeEnd;
    }

}