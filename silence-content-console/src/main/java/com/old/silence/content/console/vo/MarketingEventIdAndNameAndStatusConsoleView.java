package com.old.silence.content.console.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.old.silence.content.domain.enums.MarketingEventStatus;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Optional;

/**
 * @author yangwenchang
 */
public interface MarketingEventIdAndNameAndStatusConsoleView {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigInteger getId();

    String getName();

    MarketingEventStatus getStatus();

    @JsonIgnore
    Optional<Instant> getCreatedDate();
}
