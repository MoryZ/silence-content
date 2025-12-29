package com.old.silence.content.api.dto;


import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;
import java.util.Map;

/**
 * PoetryAnswerRecords命令对象
 */
public class PoetryAnswerRecordsCommand {
    @NotNull
    private BigInteger userId;
    @NotNull
    private BigInteger quizId;
    @NotNull
    private BigInteger contentId;
    @NotNull
    private BigInteger subCategoryId;
    @NotNull
    private Map<String, Object> userAnswer;
    @NotNull
    private Boolean correct;
    @NotNull
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

    public BigInteger getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(BigInteger subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Map<String, Object> getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(Map<String, Object> userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Boolean getCorrect() {
        return this.correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
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