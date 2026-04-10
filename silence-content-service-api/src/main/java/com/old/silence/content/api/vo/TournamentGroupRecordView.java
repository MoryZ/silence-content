package com.old.silence.content.api.vo;

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

    String getParticipantId();

    TournamentParticipantType getParticipantType();
}
