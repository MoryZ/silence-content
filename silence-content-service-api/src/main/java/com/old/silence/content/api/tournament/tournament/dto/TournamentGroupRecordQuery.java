package com.old.silence.content.api.tournament.tournament.dto;

import org.springframework.data.repository.query.parser.Part;

import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

/**
 * TournamentGroupRecord查询对象
 */
public class TournamentGroupRecordQuery {

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger groupId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger participantId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TournamentParticipantType participantType;

    public BigInteger getGroupId() {
        return groupId;
    }

    public void setGroupId(BigInteger groupId) {
        this.groupId = groupId;
    }

    public BigInteger getParticipantId() {
        return participantId;
    }

    public void setParticipantId(BigInteger participantId) {
        this.participantId = participantId;
    }

    public TournamentParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(TournamentParticipantType participantType) {
        this.participantType = participantType;
    }
}
