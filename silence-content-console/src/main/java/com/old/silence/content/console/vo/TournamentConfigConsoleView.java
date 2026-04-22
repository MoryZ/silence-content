package com.old.silence.content.console.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.old.silence.content.domain.enums.tournament.TournamentDivisionMode;
import com.old.silence.content.domain.enums.tournament.TournamentMatchMode;
import com.old.silence.content.domain.enums.tournament.TournamentRobotMode;
import com.old.silence.content.domain.enums.tournament.TournamentType;


import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;


public interface TournamentConfigConsoleView {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigInteger getId();

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigInteger getEventGameId();

    TournamentType getTournamentType();

    TournamentDivisionMode getDivisionMode();

    TournamentMatchMode getMatchMode();

    TournamentRobotMode getRobotMode();

    Instant getRegistrationStartTime();

    Instant getRegistrationEndTime();

    Instant getTournamentStartTime();

    Instant getTournamentEndTime();

    Integer getMaxParticipants();

    Integer getMinParticipants();

    Integer getGroupSize();

    Integer getMaxDailyChallenges();

    Integer getChallengeTimeoutMinutes();

    Map<String, Object> getAttributes();
}
