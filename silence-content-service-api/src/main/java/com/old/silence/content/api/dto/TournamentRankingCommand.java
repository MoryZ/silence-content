package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentRankingType;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TournamentRankingCommand {
    @NotNull
    private BigInteger eventGameId;
    @NotNull
    private BigInteger groupId;
    @NotBlank
    private String participantId;
    @NotNull
    private TournamentParticipantType participantType;
    @NotNull
    private TournamentRankingType rankingType;
    private BigDecimal score;
    private Integer rankNo;
    private String avatarUrl;
    private String nickname;

    public BigInteger getEventGameId() { return eventGameId; }
    public void setEventGameId(BigInteger eventGameId) { this.eventGameId = eventGameId; }
    public BigInteger getGroupId() { return groupId; }
    public void setGroupId(BigInteger groupId) { this.groupId = groupId; }
    public String getParticipantId() { return participantId; }
    public void setParticipantId(String participantId) { this.participantId = participantId; }
    public TournamentParticipantType getParticipantType() { return participantType; }
    public void setParticipantType(TournamentParticipantType participantType) { this.participantType = participantType; }
    public TournamentRankingType getRankingType() { return rankingType; }
    public void setRankingType(TournamentRankingType rankingType) { this.rankingType = rankingType; }
    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }
    public Integer getRankNo() { return rankNo; }
    public void setRankNo(Integer rankNo) { this.rankNo = rankNo; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}
