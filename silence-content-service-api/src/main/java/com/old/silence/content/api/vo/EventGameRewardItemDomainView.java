package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;

import com.old.silence.content.domain.enums.EventGameRewardItemClaimType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.util.Map;

@ProjectedPayload
public interface EventGameRewardItemDomainView extends AuditableView {

    BigInteger getId();

    BigInteger getEventGameId();

    BigInteger getRewardItemId();

    RewardItemView getRewardItem();

    BigInteger getRewardRuleId();

    Long getQuantity();

    Long getInventoryQuantity();

    Map<String, Object> getAttributes();

    EventGameRewardItemClaimType getClaimType();
}
