package com.old.silence.content.api.tournament.tournament.dto;


import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentRankingType;

import java.math.BigDecimal;
import java.math.BigInteger;


public class TournamentRankingDto {

    /**
     * 玩法ID
     */
    private BigInteger eventGameId;

    /**
     * 分组ID
     */
    private BigInteger groupId;

    /**
     * 参赛单位类型
     */
    private TournamentParticipantType participantType;

    /**
     * 参赛单位ID
     */
    private BigInteger participantId;

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

    public TournamentParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(TournamentParticipantType participantType) {
        this.participantType = participantType;
    }

    public BigInteger getParticipantId() {
        return participantId;
    }

    public void setParticipantId(BigInteger participantId) {
        this.participantId = participantId;
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
