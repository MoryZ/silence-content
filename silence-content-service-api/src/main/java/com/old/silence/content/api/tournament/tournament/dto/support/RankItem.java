package com.old.silence.content.api.tournament.tournament.dto.support;


import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;

import java.math.BigDecimal;

/**
 * 缓存用：排行榜单项
 * @author moryzang
 */
public class RankItem {
    private String participantId;

    private TournamentParticipantType participantType;

    private String avatarUrl;     // 机器人头像

    private String name;          // 机器人名称

    private BigDecimal totalScore;

    private Integer rank;         // 排名

    public RankItem(String participantId, TournamentParticipantType participantType, String avatarUrl,
            String name, BigDecimal totalScore, Integer rank) {
        this.participantId = participantId;
        this.participantType = participantType;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.totalScore = totalScore;
        this.rank = rank;
    }

    // Getters and Setters
    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public TournamentParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(TournamentParticipantType participantType) {
        this.participantType = participantType;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
