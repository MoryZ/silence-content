package com.old.silence.content.api.cache;

import com.old.silence.content.domain.enums.tournament.TournamentRankingType;

import java.math.BigInteger;

public final class TournamentCacheKeys {

    private TournamentCacheKeys() {
    }

    public static String tournamentList() {
        return "content:tournament:c:list";
    }

    public static String rankingList(BigInteger eventGameId, TournamentRankingType rankingType) {
        return "content:tournament:ranking:" + eventGameId + ":" + normalizeRankingType(rankingType);
    }

    public static String userRanking(BigInteger eventGameId, String participantId, TournamentRankingType rankingType) {
        return "content:tournament:ranking:user:" + eventGameId + ":" + normalizeRankingType(rankingType) + ":" + participantId;
    }

    public static String userStatus(BigInteger eventGameId, String participantId) {
        return "content:tournament:user:status:" + eventGameId + ":" + participantId;
    }

    public static String groupAssignment(BigInteger eventGameId) {
        return "content:tournament:group:assignment:" + eventGameId;
    }

    public static String finalRankingList(BigInteger eventGameId) {
        return "content:tournament:ranking:final:" + eventGameId;
    }

    private static String normalizeRankingType(TournamentRankingType rankingType) {
        return rankingType == null ? "default" : rankingType.name();
    }
}
