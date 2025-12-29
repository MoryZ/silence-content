package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;
import java.time.Instant;

/**
 * PoetryUserLearningRecord查询对象
 */
public class PoetryUserLearningRecordQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger userId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger contentId;
    @RelationalQueryProperty(name = "firstStudiedAt", type = Part.Type.GREATER_THAN_EQUAL)
    private Instant firstStudiedAtStart;

    @RelationalQueryProperty(name = "firstStudiedAt", type = Part.Type.LESS_THAN_EQUAL)
    private Instant firstStudiedAtEnd;
    @RelationalQueryProperty(name = "lastReviewedAt", type = Part.Type.GREATER_THAN_EQUAL)
    private Instant lastReviewedAtStart;

    @RelationalQueryProperty(name = "lastReviewedAt", type = Part.Type.LESS_THAN_EQUAL)
    private Instant lastReviewedAtEnd;
    @RelationalQueryProperty(name = "nextReviewAt", type = Part.Type.GREATER_THAN_EQUAL)
    private Instant nextReviewAtStart;

    @RelationalQueryProperty(name = "nextReviewAt", type = Part.Type.LESS_THAN_EQUAL)
    private Instant nextReviewAtEnd;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long reviewCount;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long learningPhase;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long studyDuration;


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

    public Instant getFirstStudiedAtStart() {
        return this.firstStudiedAtStart;
    }

    public void setFirstStudiedAtStart(Instant firstStudiedAtStart) {
        this.firstStudiedAtStart = firstStudiedAtStart;
    }

    public Instant getFirstStudiedAtEnd() {
        return this.firstStudiedAtEnd;
    }

    public void setFirstStudiedAtEnd(Instant firstStudiedAtEnd) {
        this.firstStudiedAtEnd = firstStudiedAtEnd;
    }

    public Instant getLastReviewedAtStart() {
        return this.lastReviewedAtStart;
    }

    public void setLastReviewedAtStart(Instant lastReviewedAtStart) {
        this.lastReviewedAtStart = lastReviewedAtStart;
    }

    public Instant getLastReviewedAtEnd() {
        return this.lastReviewedAtEnd;
    }

    public void setLastReviewedAtEnd(Instant lastReviewedAtEnd) {
        this.lastReviewedAtEnd = lastReviewedAtEnd;
    }

    public Instant getNextReviewAtStart() {
        return this.nextReviewAtStart;
    }

    public void setNextReviewAtStart(Instant nextReviewAtStart) {
        this.nextReviewAtStart = nextReviewAtStart;
    }

    public Instant getNextReviewAtEnd() {
        return this.nextReviewAtEnd;
    }

    public void setNextReviewAtEnd(Instant nextReviewAtEnd) {
        this.nextReviewAtEnd = nextReviewAtEnd;
    }

    public Long getReviewCount() {
        return this.reviewCount;
    }

    public void setReviewCount(Long reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Long getLearningPhase() {
        return this.learningPhase;
    }

    public void setLearningPhase(Long learningPhase) {
        this.learningPhase = learningPhase;
    }

    public Long getStudyDuration() {
        return this.studyDuration;
    }

    public void setStudyDuration(Long studyDuration) {
        this.studyDuration = studyDuration;
    }

}