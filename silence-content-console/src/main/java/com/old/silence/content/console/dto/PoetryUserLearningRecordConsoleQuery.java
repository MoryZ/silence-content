package com.old.silence.content.console.dto;

import java.math.BigInteger;
import java.time.Instant;

/**
 * PoetryUserLearningRecord查询对象
 */
public class PoetryUserLearningRecordConsoleQuery {
    private BigInteger userId;
    private BigInteger contentId;
    private Instant firstStudiedAtStart;

    private Instant firstStudiedAtEnd;
    private Instant lastReviewedAtStart;

    private Instant lastReviewedAtEnd;
    private Instant nextReviewAtStart;

    private Instant nextReviewAtEnd;
    private Long reviewCount;
    private Long learningPhase;
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