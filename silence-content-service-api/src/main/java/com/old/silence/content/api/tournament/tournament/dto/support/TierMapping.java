package com.old.silence.content.api.tournament.tournament.dto.support;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @author EX-ZHANGMENGWEI001
 */
public class TierMapping {

    @NotNull
    @Positive
    private Integer rankStart;
    @NotNull
    @Positive
    @Max(99999)
    private Integer rankEnd;

    public Integer getRankStart() {
        return rankStart;
    }

    public void setRankStart(Integer rankStart) {
        this.rankStart = rankStart;
    }

    public Integer getRankEnd() {
        return rankEnd;
    }

    public void setRankEnd(Integer rankEnd) {
        this.rankEnd = rankEnd;
    }

}
