package com.old.silence.content.api.tournament.tournament.vo;

import org.springframework.data.web.ProjectedPayload;

import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

/**
 * TournamentParticipationRecord视图接口
 */
@ProjectedPayload
public interface TournamentParticipationRecordView extends AuditableView {

    BigInteger getId();

    BigInteger getEventGameId();

    BigInteger getParticipantId();

    TournamentParticipantType getParticipantType();

    TournamentParticipantStatus getStatus();

    Instant getRegistrationTime();

    BigDecimal getTotalScore();
}
