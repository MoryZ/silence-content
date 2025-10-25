package com.old.silence.content.console.vo;

import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;

/**
 * PoetryDailyStudyPlan视图接口
 */
public interface PoetryDailyStudyPlanConsoleView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    LocalDate getPlanDate();

    String getNewItemIds();

    String getReviewItemIds();

    String getCompletedNewItems();

    String getCompletedReviewItems();

    BigDecimal getCompletionRate();

}