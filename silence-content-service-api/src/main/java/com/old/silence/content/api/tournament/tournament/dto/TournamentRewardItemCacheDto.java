package com.old.silence.content.api.tournament.tournament.dto;


import com.old.silence.content.domain.enums.EventGameRewardItemClaimType;

import java.math.BigInteger;
import java.util.Map;

public class TournamentRewardItemCacheDto {

    // 玩法奖品ID
    private BigInteger eventGameRewardItemId;

    // 总数量
    private Long quantity;

    // 可用数量
    private Long inventoryQuantity;

    // 扩展属性
    private Map<String, Object> attributes;

    // 奖品领取类型(1自动领取2手动领取)
    private EventGameRewardItemClaimType claimType;

    // 奖品发放规则
    private TournamentRewardItemRuleCacheDto rewardRule;

    public BigInteger getEventGameRewardItemId() {
        return eventGameRewardItemId;
    }

    public void setEventGameRewardItemId(BigInteger eventGameRewardItemId) {
        this.eventGameRewardItemId = eventGameRewardItemId;
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

    public EventGameRewardItemClaimType getClaimType() {
        return claimType;
    }

    public void setClaimType(EventGameRewardItemClaimType claimType) {
        this.claimType = claimType;
    }

    public TournamentRewardItemRuleCacheDto getRewardRule() {
        return rewardRule;
    }

    public void setRewardRule(TournamentRewardItemRuleCacheDto rewardRule) {
        this.rewardRule = rewardRule;
    }
}
