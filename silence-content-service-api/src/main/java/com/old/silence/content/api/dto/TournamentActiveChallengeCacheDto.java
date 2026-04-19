package com.old.silence.content.api.dto;


import com.old.silence.content.api.tournament.tournament.dto.support.ExerciseScoreCoefficient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZHAOGUANRUI108
 */
public class TournamentActiveChallengeCacheDto {
    /**
     * 挑战ID
     */
    private String challengeId;

    /**
     * 开始时间
     */
    private Instant startTime;

    /**
     * 本次挑战的运动项列表
     */
    private List<ExerciseScoreCoefficient> exerciseItems;

    public TournamentActiveChallengeCacheDto() {
    }

    public TournamentActiveChallengeCacheDto(String challengeId, Instant startTime,
                                             List<ExerciseScoreCoefficient> exerciseItems) {
        this.challengeId = challengeId;
        this.startTime = startTime;
        this.exerciseItems = new ArrayList<>(exerciseItems);
    }

    // Getters and Setters
    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public List<ExerciseScoreCoefficient> getExerciseItems() {
        return exerciseItems;
    }

    public void setExerciseItems(List<ExerciseScoreCoefficient> exerciseItems) {
        this.exerciseItems = exerciseItems;
    }
}
