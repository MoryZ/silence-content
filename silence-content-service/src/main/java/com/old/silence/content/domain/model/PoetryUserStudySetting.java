package com.old.silence.content.domain.model;

import jakarta.persistence.Entity;
import com.old.silence.data.commons.domain.AbstractAuditable;

import java.time.Instant;
import java.math.BigInteger;
@Entity
public class PoetryUserStudySetting extends AbstractAuditable<BigInteger> {
    private BigInteger userId;
    private Long dailyNewItems;
    private Long dailyReviewItems;
    private Instant studyReminderTime;
    private String enableDarkMode;
    private Long studySessionMinutes;


    public BigInteger getUserId() {
        return this.userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }
    public Long getDailyNewItems() {
        return this.dailyNewItems;
    }

    public void setDailyNewItems(Long dailyNewItems) {
        this.dailyNewItems = dailyNewItems;
    }
    public Long getDailyReviewItems() {
        return this.dailyReviewItems;
    }

    public void setDailyReviewItems(Long dailyReviewItems) {
        this.dailyReviewItems = dailyReviewItems;
    }
    public Instant getStudyReminderTime() {
        return this.studyReminderTime;
    }

    public void setStudyReminderTime(Instant studyReminderTime) {
        this.studyReminderTime = studyReminderTime;
    }
    public String getEnableDarkMode() {
        return this.enableDarkMode;
    }

    public void setEnableDarkMode(String enableDarkMode) {
        this.enableDarkMode = enableDarkMode;
    }
    public Long getStudySessionMinutes() {
        return this.studySessionMinutes;
    }

    public void setStudySessionMinutes(Long studySessionMinutes) {
        this.studySessionMinutes = studySessionMinutes;
    }
}