package com.old.silence.content.console.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.old.silence.content.domain.enums.MarketingEventStatus;
import com.old.silence.content.domain.enums.ParticipantType;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface MarketingEventAndEventGamesConsoleView {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigInteger getId();

    String getName();

    String getCode();

    String getDisplayName();

    String getDescription();

    Instant getStartTime();

    Instant getEndTime();

    String getCoverUrl();

    String getRuleContent();

    String getBackgroundImageIobsKey();

    MarketingEventStatus getStatus();

    ParticipantType getParticipantType();

    Map<String, Object> getAttributes();

    List<EventGameConsoleView> getEventGames();


}
