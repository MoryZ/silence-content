package com.old.silence.content.application.api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

/**
 * PoetryUserFavorite命令对象
 */
public class PoetryUserFavoriteApplicationCommand {
    @NotNull
    private BigInteger userId;
    @NotNull
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