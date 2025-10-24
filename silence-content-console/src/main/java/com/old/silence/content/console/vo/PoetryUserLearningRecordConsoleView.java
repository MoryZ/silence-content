package com.old.silence.content.console.vo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

import com.old.silence.data.commons.domain.AuditableView;

/**
 * PoetryUserLearningRecord视图接口
 */
public interface PoetryUserLearningRecordConsoleView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    BigInteger getContentId();

    Instant getFirstStudiedAt();

    Instant getLastReviewedAt();

    Instant getNextReviewAt();

    Long getReviewCount();

    BigDecimal getMemoryStrength();

    BigDecimal getEasinessFactor();

    Long getLearningPhase();

    String getIsRemembered();

    Long getStudyDuration();

}