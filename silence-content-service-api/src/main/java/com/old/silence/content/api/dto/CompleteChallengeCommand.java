package com.old.silence.content.api.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;
import java.util.List;

/**
 * @author ZHAOGUANRUI108
 */
public class CompleteChallengeCommand {
    @NotBlank(message = "挑战记录ID不能为空")
    private String challengeId;

    //活动Id
    @NotNull
    private BigInteger eventGameId;

    private BigInteger partyId;

    private BigInteger memberId;

    @NotEmpty(message = "项目详情不能为空")
    private List<@Valid ExerciseSubmitItem> exerciseDetails;

    // Getters and Setters
    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public List<ExerciseSubmitItem> getExerciseDetails() {
        return exerciseDetails;
    }

    public void setExerciseDetails(List<ExerciseSubmitItem> exerciseDetails) {
        this.exerciseDetails = exerciseDetails;
    }

    public @NotNull BigInteger getEventGameId() {
        return eventGameId;
    }

    public void setEventGameId(@NotNull BigInteger eventGameId) {
        this.eventGameId = eventGameId;
    }

    public BigInteger getPartyId() {
        return partyId;
    }

    public void setPartyId(BigInteger partyId) {
        this.partyId = partyId;
    }

    public BigInteger getMemberId() {
        return memberId;
    }

    public void setMemberId(BigInteger memberId) {
        this.memberId = memberId;
    }
}
