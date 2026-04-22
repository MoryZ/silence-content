package com.old.silence.content.api.tournament.tournament.dto;




import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

/**
 * TournamentParticipationRecord命令对象
 */
public class TournamentParticipationRecordCommand {

    @NotNull
    private BigInteger eventGameId;
    @NotBlank
    private BigInteger participantId;
    @NotNull
    private TournamentParticipantType participantType;
    @NotNull
    private TournamentParticipantStatus status;
    private Instant registrationTime;
    private BigDecimal totalScore;

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

    public Instant getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Instant registrationTime) {
        this.registrationTime = registrationTime;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }
}
