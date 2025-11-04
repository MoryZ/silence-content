package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

/**
* PoetryAnswerRecords查询对象
*/
public class PoetryAnswerRecordsQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger userId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger quizId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger contentId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long hintsUsed;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String sessionId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
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