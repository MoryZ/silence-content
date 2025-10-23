package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.math.BigDecimal;
import java.math.BigInteger;
/**
* PoetryDailyStudyPlan命令对象
*/
public class PoetryDailyStudyPlanCommand {
    @NotNull
    private BigInteger userId;
    @NotNull
    private Instant planDate;
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
    public Instant getPlanDate() {
        return this.planDate;
    }

    public void setPlanDate(Instant planDate) {
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