package com.old.silence.content.console.vo;

import java.math.BigInteger;
import java.time.Instant;

import com.old.silence.data.commons.domain.AuditableView;

/**
 * PoetryUserStudySetting视图接口
 */
public interface PoetryUserStudySettingConsoleView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    Long getDailyNewItems();

    Long getDailyReviewItems();

    Instant getStudyReminderTime();

    String getEnableDarkMode();

    Long getStudySessionMinutes();

}