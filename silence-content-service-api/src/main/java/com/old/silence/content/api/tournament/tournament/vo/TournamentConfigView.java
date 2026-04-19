package com.old.silence.content.api.tournament.tournament.vo;

import org.springframework.data.web.ProjectedPayload;


import com.old.silence.content.domain.enums.tournament.TournamentDivisionMode;
import com.old.silence.content.domain.enums.tournament.TournamentMatchMode;
import com.old.silence.content.domain.enums.tournament.TournamentRobotMode;
import com.old.silence.content.domain.enums.tournament.TournamentType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;

/**
 * TournamentConfig视图接口
 */
@ProjectedPayload
public interface TournamentConfigView extends AuditableView {
    BigInteger getId();

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
