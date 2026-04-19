package com.old.silence.content.api.tournament.tournament.dto;


import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;

public class TournamentUserParticipationInfoCommand {

    @NotNull
    private BigInteger eventGameId;

    private BigInteger memberId;

    @NotNull
    private BigInteger partyId;


    public BigInteger getEventGameId() {
        return eventGameId;
    }

    public void setEventGameId(BigInteger eventGameId) {
        this.eventGameId = eventGameId;
    }

    public BigInteger getMemberId() {
        return memberId;
    }

    public void setMemberId(BigInteger memberId) {
        this.memberId = memberId;
    }

    public @NotNull BigInteger getPartyId() {
        return partyId;
    }

    public void setPartyId(@NotNull BigInteger partyId) {
        this.partyId = partyId;
    }
}
