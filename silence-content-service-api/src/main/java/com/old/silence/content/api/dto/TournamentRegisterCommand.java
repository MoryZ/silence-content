package com.old.silence.content.api.dto;


import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public class TournamentRegisterCommand {

    private BigInteger memberId;

    @NotNull
    private BigInteger partyId;

    @NotNull
    private BigInteger eventGameId;

    public BigInteger getMemberId() {
        return memberId;
    }

    public void setMemberId(BigInteger memberId) {
        this.memberId = memberId;
    }

    public BigInteger getPartyId() {
        return partyId;
    }

    public void setPartyId(BigInteger partyId) {
        this.partyId = partyId;
    }

    public BigInteger getEventGameId() {
        return eventGameId;
    }

    public void setEventGameId(BigInteger eventGameId) {
        this.eventGameId = eventGameId;
    }
}
