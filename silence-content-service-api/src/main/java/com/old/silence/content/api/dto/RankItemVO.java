package com.old.silence.content.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author ZHAOGUANRUI108
 */
public class RankItemVO {
    /**
     * 排名
     */
    private int rank;

    /**
     * 参与人id
     */
    private BigInteger participantId;

    /**
     * 参与人类型
     */
    private String participantType;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 名称
     */
    private String name;

    /**
     * 总得分
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal totalScore;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public BigInteger getParticipantId() {
        return participantId;
    }

    public void setParticipantId(BigInteger participantId) {
        this.participantId = participantId;
    }

    public String getParticipantType() {
        return participantType;
    }

    public void setParticipantType(String participantType) {
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
}
