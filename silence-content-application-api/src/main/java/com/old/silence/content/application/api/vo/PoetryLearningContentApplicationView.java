package com.old.silence.content.application.api.vo;

import java.math.BigInteger;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

/**
 * PoetryLearningContent视图接口
 */
@ProjectedPayload
public interface PoetryLearningContentApplicationView extends AuditableView {
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

    Long getViewCount();

    String getIsEnabled();

}