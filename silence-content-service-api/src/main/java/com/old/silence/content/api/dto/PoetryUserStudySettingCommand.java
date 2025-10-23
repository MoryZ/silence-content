package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.math.BigInteger;
/**
* PoetryUserStudySetting命令对象
*/
public class PoetryUserStudySettingCommand {
    @NotNull
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