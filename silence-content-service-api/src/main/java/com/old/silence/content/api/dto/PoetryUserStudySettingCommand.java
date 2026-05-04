package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotNull;

import com.old.silence.content.domain.enums.StudyMode;
import com.old.silence.content.domain.enums.StudyStatus;

import java.math.BigInteger;
import java.time.LocalTime;

/**
 * PoetryUserStudySetting命令对象
 */
public class PoetryUserStudySettingCommand {
    @NotNull
    private BigInteger userId;
    @NotNull
    private BigInteger gradeId;
    @NotNull
    private BigInteger subCategoryId;
    private Long totalCount;
    private Long dailyNewCount;
    private Long dailyReviewCount;
    private StudyMode studyMode;
    private StudyStatus status;
    private LocalTime studyReminderTime;
    private Boolean enableDarkMode;
    private Long studySessionMinutes;

    public BigInteger getUserId() {
        return this.userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getGradeId() {
        return gradeId;
    }

    public void setGradeId(BigInteger gradeId) {
        this.gradeId = gradeId;
    }

    public BigInteger getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(BigInteger subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
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

    public StudyMode getStudyMode() {
        return studyMode;
    }

    public void setStudyMode(StudyMode studyMode) {
        this.studyMode = studyMode;
    }

    public StudyStatus getStatus() {
        return status;
    }

    public void setStatus(StudyStatus status) {
        this.status = status;
    }

    public LocalTime getStudyReminderTime() {
        return this.studyReminderTime;
    }

    public void setStudyReminderTime(LocalTime studyReminderTime) {
        this.studyReminderTime = studyReminderTime;
    }

    public Boolean getEnableDarkMode() {
        return this.enableDarkMode;
    }

    public void setEnableDarkMode(Boolean enableDarkMode) {
        this.enableDarkMode = enableDarkMode;
    }

    public Long getStudySessionMinutes() {
        return this.studySessionMinutes;
    }

    public void setStudySessionMinutes(Long studySessionMinutes) {
        this.studySessionMinutes = studySessionMinutes;
    }
}