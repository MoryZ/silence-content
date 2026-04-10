package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentRankingType;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

public class TournamentRankingQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger eventGameId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger groupId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private String participantId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TournamentRankingType rankingType;

    public BigInteger getEventGameId() { return eventGameId; }
    public void setEventGameId(BigInteger eventGameId) { this.eventGameId = eventGameId; }
    public BigInteger getGroupId() { return groupId; }
    public void setGroupId(BigInteger groupId) { this.groupId = groupId; }
    public String getParticipantId() { return participantId; }
    public void setParticipantId(String participantId) { this.participantId = participantId; }
    public TournamentRankingType getRankingType() { return rankingType; }
    public void setRankingType(TournamentRankingType rankingType) { this.rankingType = rankingType; }
}
