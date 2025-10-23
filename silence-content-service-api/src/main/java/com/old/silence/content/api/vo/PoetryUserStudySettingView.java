package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

import java.time.Instant;
import java.math.BigInteger;

/**
* PoetryUserStudySetting视图接口
*/
@ProjectedPayload
public interface PoetryUserStudySettingView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();
    Long getDailyNewItems();
    Long getDailyReviewItems();
    Instant getStudyReminderTime();
    String getEnableDarkMode();
    Long getStudySessionMinutes();

}