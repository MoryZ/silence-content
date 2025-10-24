package com.old.silence.content.console.vo;

import java.math.BigInteger;
import java.time.Instant;

import com.old.silence.data.commons.domain.AuditableView;

/**
 * PoetryUserLearningStats视图接口
 */
public interface PoetryUserLearningStatsConsoleView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    Long getTotalStudyDays();

    Long getTotalItemsLearned();

    Long getTotalStudyMinutes();

    Long getCurrentStreak();

    Long getLongestStreak();

    Instant getLastStudyDate();

}