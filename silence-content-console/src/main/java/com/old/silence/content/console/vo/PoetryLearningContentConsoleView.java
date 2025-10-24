package com.old.silence.content.console.vo;

import java.math.BigInteger;

import com.old.silence.data.commons.domain.AuditableView;

/**
 * PoetryLearningContent视图接口
 */
public interface PoetryLearningContentConsoleView extends AuditableView {
    BigInteger getId();

    String getTitle();

    String getSubtitle();

    Long getContentType();

    BigInteger getGradeId();

    BigInteger getCategoryId();

    BigInteger getSubcategoryId();

    Long getDifficulty();

    String getOriginalText();

    String getAuthor();

    String getDynasty();

    String getBackground();

    String getExplanation();

    String getUsageExamples();

    String getAnnotations();

    String getTranslation();

    String getAppreciation();

    String getAudioUrl();

    String getImageUrl();

    Long getViewCount();

    String getIsEnabled();

}