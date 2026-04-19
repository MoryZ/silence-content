package com.old.silence.content.api.vo;


import com.old.silence.content.domain.enums.RewardItemStatus;
import com.old.silence.content.domain.enums.RewardItemType;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Optional;

/**
 * @author YANGWENCHANG983
 */
public interface RewardItemView {

    BigInteger getId();

    Boolean getDeleted();

    Optional<String> getCreatedBy();

    Optional<Instant> getCreatedDate();

    Optional<String> getLastModifiedBy();

    Optional<Instant> getLastModifiedDate();

    String getName();

    String getDisplayName();

    RewardItemType getType();

    Long getQuantity();

    Long getInventoryQuantity();

    String getIconKey();

    String getIconUrl();

    String getDescription();

    String getReferenceId();

    RewardItemStatus getStatus();

    Object getAttributes();
}
