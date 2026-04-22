package com.old.silence.content.api.vo;


import com.old.silence.content.domain.enums.EventGameType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.time.Instant;

/**
 * @author yangwenchang
 */
public interface EventGameDomainBaseView extends AuditableView {

    BigInteger getId();

    BigInteger getEventId();

    String getName();

    EventGameType getType();

    Instant getEffectiveAt();

    Instant getExpiresAt();

    BigInteger getEntryRuleId();

    String getImageUrl();

    String getAttributes();

    String getSourceIdentifier();

}
