package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
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
    private Long dailyNewItems;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Long dailyReviewItems;
    @RelationalQueryProperty(name = "studyReminderTime", type = Part.Type.GREATER_THAN_EQUAL)
    private LocalTime studyReminderTimeStart;

    @RelationalQueryProperty(name = "studyReminderTime", type = Part.Type.LESS_THAN_EQUAL)
    private LocalTime studyReminderTimeEnd;
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