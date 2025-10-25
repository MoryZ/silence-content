package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.time.LocalTime;

/**
 * PoetryUserStudySetting视图接口
 */
@ProjectedPayload
public interface PoetryUserStudySettingView extends AuditableView {
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