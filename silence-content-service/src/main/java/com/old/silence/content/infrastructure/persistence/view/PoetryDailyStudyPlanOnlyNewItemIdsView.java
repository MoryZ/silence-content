package com.old.silence.content.infrastructure.persistence.view;


import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface PoetryDailyStudyPlanOnlyNewItemIdsView {

    BigInteger getId();

    String getNewItemIds();

    String getCompletedNewItems();
}
