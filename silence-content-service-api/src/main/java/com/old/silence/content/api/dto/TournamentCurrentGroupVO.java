package com.old.silence.content.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;

import java.math.BigInteger;

/**
 * @author ZHAOGUANRUI108
 */
public class TournamentCurrentGroupVO {
    /**
     * 参与人
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger participantId;

    /**
     * 参与人类型
     */
    private TournamentParticipantType participantType;

    /**
     * 比赛得分
     */
    private String score;

    public BigInteger getParticipantId() {
        return participantId;
    }

    public void setParticipantId(BigInteger participantId) {
        this.participantId = participantId;
    }

    public TournamentParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(TournamentParticipantType participantType) {
        this.participantType = participantType;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
