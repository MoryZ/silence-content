package com.old.silence.content.api.dto;


import com.old.silence.content.domain.enums.EventGameRewardItemClaimType;

import java.math.BigInteger;
import java.util.Map;

public class EventGameRewardItemDomainCommand {

    private BigInteger id;

    private BigInteger eventGameId;

    private BigInteger rewardItemId;

    private BigInteger rewardRuleId;

    private Long quantity;

    private Long inventoryQuantity;

    private Map<String, Object> attributes;

    private MarketingRuleDomainCommand matchRule;

    private MarketingRuleDomainCommand assistanceRule;

    private MarketingRuleDomainCommand buyInsureProductRule;

    private MarketingRuleDomainCommand claimRule;

    private MarketingRuleDomainCommand rewardRule;

    private EventGameRewardItemClaimType claimType;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getEventGameId() {
        return this.eventGameId;
    }

    public void setEventGameId(BigInteger eventGameId) {
        this.eventGameId = eventGameId;
    }

    public BigInteger getRewardItemId() {
        return this.rewardItemId;
    }

    public void setRewardItemId(BigInteger rewardItemId) {
        this.rewardItemId = rewardItemId;
    }

    public BigInteger getRewardRuleId() {
        return this.rewardRuleId;
    }

    public void setRewardRuleId(BigInteger rewardRuleId) {
        this.rewardRuleId = rewardRuleId;
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

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public MarketingRuleDomainCommand getMatchRule() {
        return matchRule;
    }

    public void setMatchRule(MarketingRuleDomainCommand matchRule) {
        this.matchRule = matchRule;
    }

    public MarketingRuleDomainCommand getAssistanceRule() {
        return assistanceRule;
    }

    public void setAssistanceRule(MarketingRuleDomainCommand assistanceRule) {
        this.assistanceRule = assistanceRule;
    }

    public MarketingRuleDomainCommand getClaimRule() {
        return claimRule;
    }

    public void setClaimRule(MarketingRuleDomainCommand claimRule) {
        this.claimRule = claimRule;
    }

    public EventGameRewardItemClaimType getClaimType() {
        return claimType;
    }

    public void setClaimType(EventGameRewardItemClaimType claimType) {
        this.claimType = claimType;
    }

    public MarketingRuleDomainCommand getBuyInsureProductRule() {
        return buyInsureProductRule;
    }

    public void setBuyInsureProductRule(MarketingRuleDomainCommand buyInsureProductRule) {
        this.buyInsureProductRule = buyInsureProductRule;
    }

    public MarketingRuleDomainCommand getRewardRule() {
        return rewardRule;
    }

    public void setRewardRule(MarketingRuleDomainCommand rewardRule) {
        this.rewardRule = rewardRule;
    }
}
