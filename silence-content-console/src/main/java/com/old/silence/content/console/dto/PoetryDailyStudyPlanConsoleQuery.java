package com.old.silence.content.console.dto;

import java.math.BigInteger;
import java.time.LocalDate;

/**
 * PoetryDailyStudyPlan查询对象
 */
public class PoetryDailyStudyPlanConsoleQuery {
    private BigInteger userId;
    private LocalDate planDateStart;

    private LocalDate planDateEnd;
    private String completedNewItems;
    private String completedReviewItems;


    public BigInteger getUserId() {
        return this.userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public LocalDate getPlanDateStart() {
        return this.planDateStart;
    }

    public void setPlanDateStart(LocalDate planDateStart) {
        this.planDateStart = planDateStart;
    }

    public LocalDate getPlanDateEnd() {
        return this.planDateEnd;
    }

    public void setPlanDateEnd(LocalDate planDateEnd) {
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