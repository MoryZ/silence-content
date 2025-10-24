package com.old.silence.content.api.dto;

import java.math.BigInteger;
import java.time.Instant;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

/**
 * PoetryDailyStudyPlan查询对象
 */
public class PoetryDailyStudyPlanQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger userId;
    @RelationalQueryProperty(name = "plan_date", type = Part.Type.GREATER_THAN_EQUAL)
    private Instant planDateStart;

    @RelationalQueryProperty(name = "plan_date", type = Part.Type.LESS_THAN_EQUAL)
    private Instant planDateEnd;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String completedNewItems;
    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
    private String completedReviewItems;


    public BigInteger getUserId() {
        return this.userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public Instant getPlanDateStart() {
        return this.planDateStart;
    }

    public void setPlanDateStart(Instant planDateStart) {
        this.planDateStart = planDateStart;
    }

    public Instant getPlanDateEnd() {
        return this.planDateEnd;
    }

    public void setPlanDateEnd(Instant planDateEnd) {
        this.planDateEnd = planDateEnd;
    }

    public String getCompletedNewItems() {
        return this.completedNewItems;
    }

    public void setCompletedNewItems(String completedNewItems) {
        this.completedNewItems = completedNewItems;
    }

    public String getCompletedReviewItems() {
        return this.completedReviewItems;
    }

    public void setCompletedReviewItems(String completedReviewItems) {
        this.completedReviewItems = completedReviewItems;
    }

}