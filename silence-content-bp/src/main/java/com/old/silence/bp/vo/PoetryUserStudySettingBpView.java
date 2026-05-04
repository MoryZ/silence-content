package com.old.silence.bp.vo;

import com.old.silence.content.domain.enums.StudyMode;
import com.old.silence.content.domain.enums.StudyStatus;

import java.math.BigInteger;
import java.time.LocalTime;

/**
 * PoetryUserStudySetting视图接口
 */
public interface PoetryUserStudySettingBpView {
    BigInteger getId();

    BigInteger getUserId();

    BigInteger getGradeId();

    BigInteger getSubCategoryId();

    Long getTotalCount();

    Long getDailyNewCount();

    Long getDailyReviewCount();

    StudyMode getStudyMode();

    StudyStatus getStatus();

    LocalTime getStudyReminderTime();

    Boolean getEnableDarkMode();

    Long getStudySessionMinutes();

}