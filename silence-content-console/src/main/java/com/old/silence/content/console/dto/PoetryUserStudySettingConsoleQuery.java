package com.old.silence.content.console.dto;

import java.math.BigInteger;
import java.time.LocalTime;

/**
 * PoetryUserStudySetting查询对象
 */
public class PoetryUserStudySettingConsoleQuery {
    private BigInteger userId;
    private Long dailyNewCount;
    private Long dailyReviewCount;
    private LocalTime studyReminderTimeStart;

    private LocalTime studyReminderTimeEnd;
    private Long studySessionMinutes;


    public BigInteger getUserId() {
        return this.userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public Long getDailyNewCount() {
        return this.dailyNewCount;
    }

    public void setDailyNewCount(Long dailyNewCount) {
        this.dailyNewCount = dailyNewCount;
    }

    public Long getDailyReviewCount() {
        return this.dailyReviewCount;
    }

    public void setDailyReviewCount(Long dailyReviewCount) {
        this.dailyReviewCount = dailyReviewCount;
    }

    public LocalTime getStudyReminderTimeStart() {
        return this.studyReminderTimeStart;
    }

    public void setStudyReminderTimeStart(LocalTime studyReminderTimeStart) {
        this.studyReminderTimeStart = studyReminderTimeStart;
    }

    public LocalTime getStudyReminderTimeEnd() {
        return this.studyReminderTimeEnd;
    }

    public void setStudyReminderTimeEnd(LocalTime studyReminderTimeEnd) {
        this.studyReminderTimeEnd = studyReminderTimeEnd;
    }

    public Long getStudySessionMinutes() {
        return this.studySessionMinutes;
    }

    public void setStudySessionMinutes(Long studySessionMinutes) {
        this.studySessionMinutes = studySessionMinutes;
    }

}