package com.old.silence.content.console.dto;

import java.math.BigInteger;

/**
 * ContentInteractionLog查询对象
 */
public class ContentInteractionLogConsoleQuery {
    private BigInteger userId;
    private BigInteger resourceId;


    public BigInteger getUserId() {
        return this.userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(BigInteger resourceId) {
        this.resourceId = resourceId;
    }

}