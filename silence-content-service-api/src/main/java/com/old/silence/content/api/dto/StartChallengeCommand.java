package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

/**
 * @author ZHAOGUANRUI108
 */
public class StartChallengeCommand {
    @NotNull
    private BigInteger eventGameId;

    private BigInteger memberId;

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

    public BigInteger getPartyId() {
        return partyId;
    }

    public void setPartyId(BigInteger partyId) {
        this.partyId = partyId;
    }
}
