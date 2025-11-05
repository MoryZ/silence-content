package com.old.silence.content.application.api.vo;

import java.math.BigInteger;
import java.time.Instant;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

/**
 * PoetryUserLearningStats视图接口
 */
@ProjectedPayload
public interface PoetryUserLearningStatsApplicationView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();

    Long getTotalStudyDays();

    Long getTotalItemsLearned();

    Long getTotalStudyMinutes();

    Long getCurrentStreak();

    Long getLongestStreak();

    Instant getLastStudyDate();

}