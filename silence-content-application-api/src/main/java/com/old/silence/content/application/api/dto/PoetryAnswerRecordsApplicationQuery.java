package com.old.silence.content.application.api.dto;

import java.math.BigInteger;


/**
* PoetryAnswerRecords查询对象
*/
public class PoetryAnswerRecordsApplicationQuery {
    private BigInteger userId;
    private BigInteger quizId;
    private BigInteger contentId;
    private Long hintsUsed;
    private String sessionId;
    private Long responseTime;


    public BigInteger getUserId() {
        return this.userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }
    public BigInteger getQuizId() {
        return this.quizId;
    }

    public void setQuizId(BigInteger quizId) {
        this.quizId = quizId;
    }
    public BigInteger getContentId() {
        return this.contentId;
    }

    public void setContentId(BigInteger contentId) {
        this.contentId = contentId;
    }
    public Long getHintsUsed() {
        return this.hintsUsed;
    }

    public void setHintsUsed(Long hintsUsed) {
        this.hintsUsed = hintsUsed;
    }
    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public Long getResponseTime() {
        return this.responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

}