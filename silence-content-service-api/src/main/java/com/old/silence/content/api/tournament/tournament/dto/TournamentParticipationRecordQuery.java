package com.old.silence.content.api.tournament.tournament.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

/**
 * TournamentParticipationRecord查询对象
 */
public class TournamentParticipationRecordQuery {

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger eventGameId;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger participantId;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TournamentParticipantType participantType;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TournamentParticipantStatus status;

    public BigInteger getEventGameId() {
        return eventGameId;
    }

    public void setEventGameId(BigInteger eventGameId) {
        this.eventGameId = eventGameId;
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

    public TournamentParticipantStatus getStatus() {
        return status;
    }

    public void setStatus(TournamentParticipantStatus status) {
        this.status = status;
    }
}
