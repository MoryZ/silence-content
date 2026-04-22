package com.old.silence.content.api.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigDecimal;

/**
 * @author ZHAOGUANRUI108
 */
public class TournamentFinishVO {
    private String challengeId;

    /**
     * 基础分数
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal basicScore;

    /**
     * 最终分数
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal finalScore;

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public BigDecimal getBasicScore() {
        return basicScore;
    }

    public void setBasicScore(BigDecimal basicScore) {
        this.basicScore = basicScore;
    }

    public BigDecimal getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(BigDecimal finalScore) {
        this.finalScore = finalScore;
    }
}
