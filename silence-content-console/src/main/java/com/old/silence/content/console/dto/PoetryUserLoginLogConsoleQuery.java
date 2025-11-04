package com.old.silence.content.console.dto;


import java.time.Instant;
import java.math.BigInteger;

/**
* PoetryUserLoginLog查询对象
*/
public class PoetryUserLoginLogConsoleQuery {
    private BigInteger userId;
    private String openid;

    private Long loginType;
    private String ipAddress;

    private String sessionKey;

    private Boolean loginStatus;

    private Instant loginTimeStart;

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