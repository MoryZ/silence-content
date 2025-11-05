package com.old.silence.content.application.api.vo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

/**
 * PoetryUserLearningRecord视图接口
 */
@ProjectedPayload
public interface PoetryUserLearningRecordApplicationView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    BigInteger getSubCategoryId();

    BigInteger getContentId();

    Instant getFirstStudiedAt();

    Instant getLastReviewedAt();

    Instant getNextReviewAt();

    Long getReviewCount();

    BigDecimal getMemoryStrength();

    BigDecimal getEasinessFactor();

    Long getLearningPhase();

    String getRemembered();

    Long getStudyDuration();

}