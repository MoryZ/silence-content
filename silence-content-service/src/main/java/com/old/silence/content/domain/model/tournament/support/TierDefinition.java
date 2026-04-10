package com.old.silence.content.domain.model.tournament.support;

/**
 * @author moryzang
 */
public class TierDefinition {

    private int targetGroupNumber;
    private String targetTierName;
    private int targetTierOrder;
    private String description;

    public int getTargetGroupNumber() {
        return targetGroupNumber;
    }

    public void setTargetGroupNumber(int targetGroupNumber) {
        this.targetGroupNumber = targetGroupNumber;
    }

    public String getTargetTierName() {
        return targetTierName;
    }

    public void setTargetTierName(String targetTierName) {
        this.targetTierName = targetTierName;
    }

    public int getTargetTierOrder() {
        return targetTierOrder;
    }

    public void setTargetTierOrder(int targetTierOrder) {
        this.targetTierOrder = targetTierOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
