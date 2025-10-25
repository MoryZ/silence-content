package com.old.silence.content.console.vo;

import java.math.BigDecimal;

/**
 * @author moryzang
 */
public class PoetryLearningProgressVo {
    private String newItemIds;
    private String reviewItemIds;
    private String completedNewItems;
    private String completedReviewItems;
    private BigDecimal completionRate;

    public PoetryLearningProgressVo(String newItemIds, String reviewItemIds, String completedNewItems, String completedReviewItems, BigDecimal completionRate) {
        this.newItemIds = newItemIds;
        this.reviewItemIds = reviewItemIds;
        this.completedNewItems = completedNewItems;
        this.completedReviewItems = completedReviewItems;
        this.completionRate = completionRate;
    }

    public String getNewItemIds() {
        return newItemIds;
    }

    public void setNewItemIds(String newItemIds) {
        this.newItemIds = newItemIds;
    }

    public String getReviewItemIds() {
        return reviewItemIds;
    }

    public void setReviewItemIds(String reviewItemIds) {
        this.reviewItemIds = reviewItemIds;
    }

    public String getCompletedNewItems() {
        return completedNewItems;
    }

    public void setCompletedNewItems(String completedNewItems) {
        this.completedNewItems = completedNewItems;
    }

    public String getCompletedReviewItems() {
        return completedReviewItems;
    }

    public void setCompletedReviewItems(String completedReviewItems) {
        this.completedReviewItems = completedReviewItems;
    }

    public BigDecimal getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(BigDecimal completionRate) {
        this.completionRate = completionRate;
    }
}
