package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

import java.time.Instant;
import java.math.BigInteger;

/**
* PoetryUserLearningStats视图接口
*/
@ProjectedPayload
public interface PoetryUserLearningStatsView extends AuditableView {
    BigInteger getId();

    BigInteger getUserId();
    Long getTotalStudyDays();
    Long getTotalItemsLearned();
    Long getTotalStudyMinutes();
    Long getCurrentStreak();
    Long getLongestStreak();
    Instant getLastStudyDate();

}