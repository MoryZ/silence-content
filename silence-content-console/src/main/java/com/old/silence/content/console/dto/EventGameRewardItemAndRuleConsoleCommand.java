package com.old.silence.content.console.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.old.silence.content.domain.enums.EventGameRewardItemClaimType;
import com.old.silence.validation.group.UpdateValidation;


import java.math.BigInteger;

public class EventGameRewardItemAndRuleConsoleCommand {

    @NotNull(groups = UpdateValidation.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigInteger id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigInteger eventGameId;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigInteger rewardItemId;

    @Min(-1)
    private Long quantity;

    @Min(-1)
    private Long inventoryQuantity;

    @NotNull(groups = UpdateValidation.class)
    private EventGameRewardItemClaimType claimType;

    @Valid
    private EventGameRewardItemRuleConsoleCommand eventGameRewardItemRule;


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getEventGameId() {
        return eventGameId;
    }

    public void setEventGameId(BigInteger eventGameId) {
        this.eventGameId = eventGameId;
    }

    public BigInteger getRewardItemId() {
        return rewardItemId;
    }

    public void setRewardItemId(BigInteger rewardItemId) {
        this.rewardItemId = rewardItemId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(Long inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public EventGameRewardItemClaimType getClaimType() {
        return claimType;
    }

    public void setClaimType(EventGameRewardItemClaimType claimType) {
        this.claimType = claimType;
    }

    public EventGameRewardItemRuleConsoleCommand getEventGameRewardItemRule() {
        return eventGameRewardItemRule;
    }

    public void setEventGameRewardItemRule(EventGameRewardItemRuleConsoleCommand eventGameRewardItemRule) {
        this.eventGameRewardItemRule = eventGameRewardItemRule;
    }
}
