package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

import java.time.Instant;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
* PoetryUserLearningRecord视图接口
*/
@ProjectedPayload
public interface PoetryUserLearningRecordView extends AuditableView {
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