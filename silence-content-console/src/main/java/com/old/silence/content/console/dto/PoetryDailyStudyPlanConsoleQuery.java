package com.old.silence.content.console.dto;

import java.math.BigInteger;
import java.time.Instant;

/**
 * PoetryDailyStudyPlan查询对象
 */
public class PoetryDailyStudyPlanConsoleQuery {
    private BigInteger userId;
    private Instant planDateStart;

    private Instant planDateEnd;
    private String completedNewItems;
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