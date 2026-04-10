package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

public class TournamentChallengeRecordQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger eventGameId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private String participantId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TournamentParticipantType participantType;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TournamentChallengeStatus status;

    public BigInteger getEventGameId() { return eventGameId; }
    public void setEventGameId(BigInteger eventGameId) { this.eventGameId = eventGameId; }
    public String getParticipantId() { return participantId; }
    public void setParticipantId(String participantId) { this.participantId = participantId; }
    public TournamentParticipantType getParticipantType() { return participantType; }
    public void setParticipantType(TournamentParticipantType participantType) { this.participantType = participantType; }
    public TournamentChallengeStatus getStatus() { return status; }
    public void setStatus(TournamentChallengeStatus status) { this.status = status; }
}
