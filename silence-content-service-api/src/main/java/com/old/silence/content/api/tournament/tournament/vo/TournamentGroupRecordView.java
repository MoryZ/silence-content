package com.old.silence.content.api.tournament.tournament.vo;

import org.springframework.data.web.ProjectedPayload;

import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
 * TournamentGroupRecord视图接口
 */
@ProjectedPayload
public interface TournamentGroupRecordView extends AuditableView {

    BigInteger getId();

    BigInteger getGroupId();

    BigInteger getParticipantId();

    TournamentParticipantType getParticipantType();
}
