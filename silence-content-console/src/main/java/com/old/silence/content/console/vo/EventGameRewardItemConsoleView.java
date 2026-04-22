package com.old.silence.content.console.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.old.silence.content.domain.enums.EventGameRewardItemClaimType;

import java.math.BigInteger;
import java.util.Map;

public interface EventGameRewardItemConsoleView {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigInteger getId();

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigInteger getEventGameId();

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigInteger getRewardItemId();

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigInteger getRewardRuleId();

    Long getQuantity();

    Long getInventoryQuantity();

    Map<String, Object> getAttributes();

    EventGameRewardItemClaimType getClaimType();
}
