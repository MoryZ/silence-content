package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;
import java.time.Instant;

/**
 * PoetryUserLearningStats命令对象
 */
public class PoetryUserLearningStatsCommand {
    @NotNull
    private BigInteger userId;
    private Long totalStudyDays;
    private Long totalItemsLearned;
    private Long totalStudyMinutes;
    private Long currentStreak;
    private Long longestStreak;
    private Instant lastStudyDate;

    public BigInteger getUserId() {
        return this.userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public Long getTotalStudyDays() {
        return this.totalStudyDays;
    }

    public void setTotalStudyDays(Long totalStudyDays) {
        this.totalStudyDays = totalStudyDays;
    }

    public Long getTotalItemsLearned() {
        return this.totalItemsLearned;
    }

    public void setTotalItemsLearned(Long totalItemsLearned) {
        this.totalItemsLearned = totalItemsLearned;
    }

    public Long getTotalStudyMinutes() {
        return this.totalStudyMinutes;
    }

    public void setTotalStudyMinutes(Long totalStudyMinutes) {
        this.totalStudyMinutes = totalStudyMinutes;
    }

    public Long getCurrentStreak() {
        return this.currentStreak;
    }

    public void setCurrentStreak(Long currentStreak) {
        this.currentStreak = currentStreak;
    }

    public Long getLongestStreak() {
        return this.longestStreak;
    }

    public void setLongestStreak(Long longestStreak) {
        this.longestStreak = longestStreak;
    }

    public Instant getLastStudyDate() {
        return this.lastStudyDate;
    }

    public void setLastStudyDate(Instant lastStudyDate) {
        this.lastStudyDate = lastStudyDate;
    }
}