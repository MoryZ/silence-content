package com.old.silence.content.application.api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

/**
 * PoetryDailyStudyPlan命令对象
 */
public class PoetryDailyStudyPlanApplicationCommand {
    @NotNull
    private BigInteger userId;
    @NotNull
    private LocalDate planDate;
    private String newItemIds;
    private String reviewItemIds;
    private String completedNewItems;
    private String completedReviewItems;
    private BigDecimal completionRate;

    public BigInteger getUserId() {
        return this.userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public LocalDate getPlanDate() {
        return this.planDate;
    }

    public void setPlanDate(LocalDate planDate) {
        this.planDate = planDate;
    }

    public String getNewItemIds() {
        return this.newItemIds;
    }

    public void setNewItemIds(String newItemIds) {
        this.newItemIds = newItemIds;
    }

    public String getReviewItemIds() {
        return this.reviewItemIds;
    }

    public void setReviewItemIds(String reviewItemIds) {
        this.reviewItemIds = reviewItemIds;
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

    public BigDecimal getCompletionRate() {
        return this.completionRate;
    }

    public void setCompletionRate(BigDecimal completionRate) {
        this.completionRate = completionRate;
    }
}