package com.old.silence.content.application.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigInteger;
import java.time.Instant;

import com.old.silence.content.domain.enums.LoginType;
/**
* PoetryUserLoginLog命令对象
*/
public class PoetryUserLoginLogApplicationCommand {
    @NotNull
    private BigInteger userId;
    @NotBlank
    @Size(max = 64)
    private String openid;
    @NotNull
    private LoginType loginType;
    private String ipAddress;
    private String sessionKey;
    @NotNull
    private Boolean loginStatus;
    @NotNull
    private Instant loginTime;

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
    public LoginType getLoginType() {
        return this.loginType;
    }

    public void setLoginType(LoginType loginType) {
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
    public Instant getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(Instant loginTime) {
        this.loginTime = loginTime;
    }
}