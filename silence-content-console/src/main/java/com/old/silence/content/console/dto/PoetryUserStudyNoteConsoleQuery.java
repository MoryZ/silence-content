package com.old.silence.content.console.dto;

import java.math.BigInteger;

/**
 * PoetryUserStudyNote查询对象
 */
public class PoetryUserStudyNoteConsoleQuery {
    private BigInteger userId;
    private BigInteger contentId;
    private String tags;


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

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

}