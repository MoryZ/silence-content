package com.old.silence.content.console.vo;

import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

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

    BigInteger getSubCategoryId();

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

    Boolean getEnabled();

}