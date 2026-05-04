package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.StudyStatus;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;
import java.time.LocalTime;

/**
 * PoetryUserStudySetting查询对象
 */
public class PoetryUserStudySettingQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger userId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long dailyNewCount;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long dailyReviewCount;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private StudyStatus status;

    @RelationalQueryProperty(name = "studyReminderTime", type = Part.Type.GREATER_THAN_EQUAL)
    private LocalTime studyReminderTimeStart;

    @RelationalQueryProperty(name = "studyReminderTime", type = Part.Type.LESS_THAN_EQUAL)
    private LocalTime studyReminderTimeEnd;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long studySessionMinutes;


    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public Long getDailyNewCount() {
        return dailyNewCount;
    }

    public void setDailyNewCount(Long dailyNewCount) {
        this.dailyNewCount = dailyNewCount;
    }

    public Long getDailyReviewCount() {
        return dailyReviewCount;
    }

    public void setDailyReviewCount(Long dailyReviewCount) {
        this.dailyReviewCount = dailyReviewCount;
    }

    public StudyStatus getStatus() {
        return status;
    }

    public void setStatus(StudyStatus status) {
        this.status = status;
    }

    public LocalTime getStudyReminderTimeStart() {
        return studyReminderTimeStart;
    }

    public void setStudyReminderTimeStart(LocalTime studyReminderTimeStart) {
        this.studyReminderTimeStart = studyReminderTimeStart;
    }

    public LocalTime getStudyReminderTimeEnd() {
        return studyReminderTimeEnd;
    }

    public void setStudyReminderTimeEnd(LocalTime studyReminderTimeEnd) {
        this.studyReminderTimeEnd = studyReminderTimeEnd;
    }

    public Long getStudySessionMinutes() {
        return studySessionMinutes;
    }

    public void setStudySessionMinutes(Long studySessionMinutes) {
        this.studySessionMinutes = studySessionMinutes;
    }
}