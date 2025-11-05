package com.old.silence.content.application.api.vo;

import java.math.BigInteger;
import java.time.LocalTime;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

/**
 * PoetryUserStudySetting视图接口
 */
@ProjectedPayload
public interface PoetryUserStudySettingApplicationView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    BigInteger getGradeId();

    BigInteger getSubCategoryId();

    Long getDailyNewItems();

    Long getDailyReviewItems();

    LocalTime getStudyReminderTime();

    Boolean getEnableDarkMode();

    Long getStudySessionMinutes();

}