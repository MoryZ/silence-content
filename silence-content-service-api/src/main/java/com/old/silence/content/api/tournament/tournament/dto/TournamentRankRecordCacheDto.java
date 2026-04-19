package com.old.silence.content.api.tournament.tournament.dto;



import com.old.silence.content.domain.enums.tournament.TournamentRankingType;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TournamentRankRecordCacheDto {

    private BigInteger eventGameId;

    /**
     * 分组ID
     */
    private BigInteger groupId;


    /**
     * 排行榜类型
     */
    private TournamentRankingType rankingType;

    /**
     * 分数
     */
    private BigDecimal score;

    /**
     * 排名
     */
    private Integer rankNo;

    public BigInteger getEventGameId() {
        return eventGameId;
    }

    public void setEventGameId(BigInteger eventGameId) {
        this.eventGameId = eventGameId;
    }

    public BigInteger getGroupId() {
        return groupId;
    }

    public void setGroupId(BigInteger groupId) {
        this.groupId = groupId;
    }

    public TournamentRankingType getRankingType() {
        return rankingType;
    }

    public void setRankingType(TournamentRankingType rankingType) {
        this.rankingType = rankingType;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getRankNo() {
        return rankNo;
    }

    public void setRankNo(Integer rankNo) {
        this.rankNo = rankNo;
    }
}
