package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.time.Instant;
import java.math.BigInteger;

/**
* PoetryUserStudySetting查询对象
*/
public class PoetryUserStudySettingQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger userId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long dailyNewItems;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long dailyReviewItems;
    @RelationalQueryProperty(name = "study_reminder_time", type = Part.Type.GREATER_THAN_EQUAL)
    private Instant studyReminderTimeStart;

    @RelationalQueryProperty(name = "study_reminder_time", type = Part.Type.LESS_THAN_EQUAL)
    private Instant studyReminderTimeEnd;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
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
    public Instant getStudyReminderTimeStart() {
        return this.studyReminderTimeStart;
    }

    public void setStudyReminderTimeStart(Instant studyReminderTimeStart) {
        this.studyReminderTimeStart = studyReminderTimeStart;
    }

    public Instant getStudyReminderTimeEnd() {
        return this.studyReminderTimeEnd;
    }

    public void setStudyReminderTimeEnd(Instant studyReminderTimeEnd) {
        this.studyReminderTimeEnd = studyReminderTimeEnd;
    }
    public Long getStudySessionMinutes() {
        return this.studySessionMinutes;
    }

    public void setStudySessionMinutes(Long studySessionMinutes) {
        this.studySessionMinutes = studySessionMinutes;
    }

}