package com.old.silence.content.api.vo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

/**
 * PoetryDailyStudyPlan视图接口
 */
@ProjectedPayload
public interface PoetryDailyStudyPlanView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    Instant getPlanDate();

    String getNewItemIds();

    String getReviewItemIds();

    String getCompletedNewItems();

    String getCompletedReviewItems();

    BigDecimal getCompletionRate();

}