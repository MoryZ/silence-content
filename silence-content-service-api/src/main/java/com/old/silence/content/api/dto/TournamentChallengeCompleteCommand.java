package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Map;

public class TournamentChallengeCompleteCommand {

    private BigDecimal baseScore;

    private BigDecimal finalScore;

    private Map<String, Object> exerciseDetails;

    @NotNull
    private com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus status;

    public BigDecimal getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(BigDecimal baseScore) {
        this.baseScore = baseScore;
    }

    public BigDecimal getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(BigDecimal finalScore) {
        this.finalScore = finalScore;
    }

    public Map<String, Object> getExerciseDetails() {
        return exerciseDetails;
    }

    public void setExerciseDetails(Map<String, Object> exerciseDetails) {
        this.exerciseDetails = exerciseDetails;
    }

    public com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus getStatus() {
        return status;
    }

    public void setStatus(com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus status) {
        this.status = status;
    }
}
