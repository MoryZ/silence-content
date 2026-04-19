package com.old.silence.content.api.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.old.silence.content.domain.enums.tournament.TournamentRankingType;

import java.math.BigInteger;

/**
 * @author ZHAOGUANRUI108
 */
public class TournamentGlobalRankQuery {
    @NotNull
    private BigInteger eventGameId;

    @NotNull
    private TournamentRankingType rankType;

    private BigInteger groupId;

    public BigInteger getEventGameId() {
        return eventGameId;
    }

    public void setEventGameId(BigInteger eventGameId) {
        this.eventGameId = eventGameId;
    }

    public TournamentRankingType getRankType() {
        return rankType;
    }

    public void setRankType(TournamentRankingType rankType) {
        this.rankType = rankType;
    }

    public BigInteger getGroupId() {
        return groupId;
    }

    public void setGroupId(BigInteger groupId) {
        this.groupId = groupId;
    }
}
