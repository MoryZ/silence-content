package com.old.silence.content.console.dto;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalTime;

/**
 * PoetryUserStudySetting查询对象
 */
public class PoetryUserStudySettingConsoleQuery {
    private BigInteger userId;
    private Long dailyNewItems;
    private Long dailyReviewItems;
    private LocalTime studyReminderTimeStart;

    private LocalTime  studyReminderTimeEnd;
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

    public LocalTime  getStudyReminderTimeStart() {
        return this.studyReminderTimeStart;
    }

    public void setStudyReminderTimeStart(LocalTime  studyReminderTimeStart) {
        this.studyReminderTimeStart = studyReminderTimeStart;
    }

    public LocalTime  getStudyReminderTimeEnd() {
        return this.studyReminderTimeEnd;
    }

    public void setStudyReminderTimeEnd(LocalTime  studyReminderTimeEnd) {
        this.studyReminderTimeEnd = studyReminderTimeEnd;
    }

    public Long getStudySessionMinutes() {
        return this.studySessionMinutes;
    }

    public void setStudySessionMinutes(Long studySessionMinutes) {
        this.studySessionMinutes = studySessionMinutes;
    }

}