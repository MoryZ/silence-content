package com.old.silence.content.domain.model.tournament.support;

/**
 * @author moryzang
 */
public class TierMapping {

    private int rankStart;
    private int rankEnd;
    private int tierNumber;

    public int getRankStart() {
        return rankStart;
    }

    public void setRankStart(int rankStart) {
        this.rankStart = rankStart;
    }

    public int getRankEnd() {
        return rankEnd;
    }

    public void setRankEnd(int rankEnd) {
        this.rankEnd = rankEnd;
    }

    public int getTierNumber() {
        return tierNumber;
    }

    public void setTierNumber(int tierNumber) {
        this.tierNumber = tierNumber;
    }
}
