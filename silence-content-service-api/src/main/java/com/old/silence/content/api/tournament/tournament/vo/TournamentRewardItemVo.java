package com.old.silence.content.api.tournament.tournament.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.old.silence.content.domain.enums.EventGameRewardItemClaimType;

import java.math.BigInteger;
import java.util.Map;

public class TournamentRewardItemVo {

    // 玩法奖品ID
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigInteger eventGameRewardItemId;

    // 扩展属性
    private Map<String, Object> attributes;

    // 奖品领取类型(1自动领取2手动领取)
    private EventGameRewardItemClaimType claimType;

    public BigInteger getEventGameRewardItemId() {
        return eventGameRewardItemId;
    }

    public void setEventGameRewardItemId(BigInteger eventGameRewardItemId) {
        this.eventGameRewardItemId = eventGameRewardItemId;
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
}
