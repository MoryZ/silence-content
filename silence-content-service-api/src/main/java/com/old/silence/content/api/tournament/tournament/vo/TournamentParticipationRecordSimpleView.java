package com.old.silence.content.api.tournament.tournament.vo;

import org.springframework.data.web.ProjectedPayload;

import java.math.BigInteger;


@ProjectedPayload
public interface TournamentParticipationRecordSimpleView{

    BigInteger getId();

    BigInteger getParticipantId();

}
