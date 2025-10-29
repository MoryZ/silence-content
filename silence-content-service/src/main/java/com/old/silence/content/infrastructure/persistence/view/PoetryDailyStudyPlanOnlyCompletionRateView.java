package com.old.silence.content.infrastructure.persistence.view;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface PoetryDailyStudyPlanOnlyCompletionRateView {

    BigInteger getId();

    String getNewItemIds();

    String getCompletedNewItems();

    BigDecimal getCompletionRate();
}
