package com.old.silence.content.console.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.old.silence.content.domain.enums.EventGameType;

import java.math.BigInteger;
import java.time.Instant;

/**
 * @author yangwenchang
 */
public interface EventGameConsoleView {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigInteger getId();

    String getName();

    EventGameType getType();

    Instant getEffectiveAt();

    Instant getExpiresAt();

    MarketingEventIdAndNameAndStatusConsoleView getEvent();

    BigInteger getEntryRuleId();

    String getImageUrl();

    String getAttributes();

    String getSourceIdentifier();

}
