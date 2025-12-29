package com.old.silence.content.console.dto;


import java.math.BigInteger;
import java.time.Instant;

/**
 * PoetryAnswerRecords查询对象
 */
public class PoetryAnswerRecordsConsoleQuery {
    private BigInteger userId;
    private BigInteger quizId;
    private BigInteger contentId;
    private String sessionId;
    private Instant createdDateStart;
    private Instant createdDateEnd;


    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getQuizId() {
        return quizId;
    }

    public void setQuizId(BigInteger quizId) {
        this.quizId = quizId;
    }

    public BigInteger getContentId() {
        return contentId;
    }

    public void setContentId(BigInteger contentId) {
        this.contentId = contentId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Instant getCreatedDateStart() {
        return createdDateStart;
    }

    public void setCreatedDateStart(Instant createdDateStart) {
        this.createdDateStart = createdDateStart;
    }

    public Instant getCreatedDateEnd() {
        return createdDateEnd;
    }

    public void setCreatedDateEnd(Instant createdDateEnd) {
        this.createdDateEnd = createdDateEnd;
    }
}