package com.old.silence.content.console.dto;

import java.math.BigInteger;

/**
 * PoetryUserFavorite查询对象
 */
public class PoetryUserFavoriteConsoleQuery {
    private BigInteger userId;
    private BigInteger contentId;


    public BigInteger getUserId() {
        return this.userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getContentId() {
        return this.contentId;
    }

    public void setContentId(BigInteger contentId) {
        this.contentId = contentId;
    }

}