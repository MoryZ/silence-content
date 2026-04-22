package com.old.silence.content.api.vo;



import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;

import java.math.BigInteger;
import java.time.Instant;

public class TournamentRegisterVo {

    private BigInteger participantRecordId;

    private BigInteger eventGameId;

    private BigInteger participantId;

    private TournamentParticipantType participantType;

    private Instant registrationTime;

    public BigInteger getParticipantRecordId() {
        return participantRecordId;
    }

    public void setParticipantRecordId(BigInteger participantRecordId) {
        this.participantRecordId = participantRecordId;
    }

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

    public Instant getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Instant registrationTime) {
        this.registrationTime = registrationTime;
    }
}
