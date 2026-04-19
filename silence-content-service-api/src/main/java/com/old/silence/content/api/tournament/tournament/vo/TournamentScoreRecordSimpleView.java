package com.old.silence.content.api.tournament.tournament.vo;

import org.springframework.data.web.ProjectedPayload;

import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;

import java.math.BigDecimal;
import java.math.BigInteger;

@ProjectedPayload
public interface TournamentScoreRecordSimpleView {

    BigInteger getId();

    BigInteger getParticipantId();

    TournamentParticipantType getParticipantType();

    BigDecimal getScore();

}
