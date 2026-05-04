package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.StudyMode;
import com.old.silence.content.domain.enums.StudyStatus;
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

    Long getTotalCount();

    Long getDailyNewCount();

    Long getDailyReviewCount();

    StudyMode getStudyMode();

    StudyStatus getStatus();

    LocalTime getStudyReminderTime();

    Boolean getEnableDarkMode();

    Long getStudySessionMinutes();

}