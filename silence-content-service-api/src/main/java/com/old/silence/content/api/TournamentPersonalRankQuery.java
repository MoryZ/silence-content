package com.old.silence.content.api;


import jakarta.validation.constraints.NotNull;
import com.old.silence.content.domain.enums.tournament.TournamentRankingType;

import java.math.BigInteger;

/**
 * @author ZHAOGUANRUI108
 */
public class TournamentPersonalRankQuery {
    @NotNull
    private BigInteger eventGameId;

    private BigInteger memberId;

    private BigInteger partyId;

    @NotNull
    private TournamentRankingType rankType;

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

    public TournamentRankingType getRankType() {
        return rankType;
    }

    public void setRankType(TournamentRankingType rankType) {
        this.rankType = rankType;
    }
}
