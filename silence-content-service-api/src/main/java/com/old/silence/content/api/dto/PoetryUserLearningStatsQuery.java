package com.old.silence.content.api.dto;

import java.math.BigInteger;
import java.time.Instant;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

/**
 * PoetryUserLearningStats查询对象
 */
public class PoetryUserLearningStatsQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger userId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long totalStudyDays;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long totalItemsLearned;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long totalStudyMinutes;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long currentStreak;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long longestStreak;
    @RelationalQueryProperty(name = "last_study_date", type = Part.Type.GREATER_THAN_EQUAL)
    private Instant lastStudyDateStart;

    @RelationalQueryProperty(name = "last_study_date", type = Part.Type.LESS_THAN_EQUAL)
    private Instant lastStudyDateEnd;


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

    public Instant getLastStudyDateStart() {
        return this.lastStudyDateStart;
    }

    public void setLastStudyDateStart(Instant lastStudyDateStart) {
        this.lastStudyDateStart = lastStudyDateStart;
    }

    public Instant getLastStudyDateEnd() {
        return this.lastStudyDateEnd;
    }

    public void setLastStudyDateEnd(Instant lastStudyDateEnd) {
        this.lastStudyDateEnd = lastStudyDateEnd;
    }

}