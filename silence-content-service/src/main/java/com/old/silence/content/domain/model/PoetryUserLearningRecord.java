package com.old.silence.content.domain.model;

import jakarta.persistence.Entity;
import com.old.silence.data.commons.domain.AbstractAuditable;

import java.time.Instant;
import java.math.BigDecimal;
import java.math.BigInteger;
@Entity
public class PoetryUserLearningRecord extends AbstractAuditable<BigInteger> {
    private BigInteger userId;
    private BigInteger contentId;
    private Instant firstStudiedAt;
    private Instant lastReviewedAt;
    private Instant nextReviewAt;
    private Long reviewCount;
    private BigDecimal memoryStrength;
    private BigDecimal easinessFactor;
    private Long learningPhase;
    private String isRemembered;
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
    public Instant getFirstStudiedAt() {
        return this.firstStudiedAt;
    }

    public void setFirstStudiedAt(Instant firstStudiedAt) {
        this.firstStudiedAt = firstStudiedAt;
    }
    public Instant getLastReviewedAt() {
        return this.lastReviewedAt;
    }

    public void setLastReviewedAt(Instant lastReviewedAt) {
        this.lastReviewedAt = lastReviewedAt;
    }
    public Instant getNextReviewAt() {
        return this.nextReviewAt;
    }

    public void setNextReviewAt(Instant nextReviewAt) {
        this.nextReviewAt = nextReviewAt;
    }
    public Long getReviewCount() {
        return this.reviewCount;
    }

    public void setReviewCount(Long reviewCount) {
        this.reviewCount = reviewCount;
    }
    public BigDecimal getMemoryStrength() {
        return this.memoryStrength;
    }

    public void setMemoryStrength(BigDecimal memoryStrength) {
        this.memoryStrength = memoryStrength;
    }
    public BigDecimal getEasinessFactor() {
        return this.easinessFactor;
    }

    public void setEasinessFactor(BigDecimal easinessFactor) {
        this.easinessFactor = easinessFactor;
    }
    public Long getLearningPhase() {
        return this.learningPhase;
    }

    public void setLearningPhase(Long learningPhase) {
        this.learningPhase = learningPhase;
    }
    public String getIsRemembered() {
        return this.isRemembered;
    }

    public void setIsRemembered(String isRemembered) {
        this.isRemembered = isRemembered;
    }
    public Long getStudyDuration() {
        return this.studyDuration;
    }

    public void setStudyDuration(Long studyDuration) {
        this.studyDuration = studyDuration;
    }
}