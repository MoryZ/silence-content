package com.old.silence.content.api.vo;


import com.old.silence.content.api.dto.ExerciseDetailVO;

import java.util.List;

/**
 * @author ZHAOGUANRUI108
 */
public class TournamentStartVO {
    /**
     * 挑战记录ID
     */
    private String challengeId;

    /**
     * 比赛项目列表
     */
    private List<ExerciseDetailVO> exercises;

    // Getters and Setters

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public List<ExerciseDetailVO> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseDetailVO> exercises) {
        this.exercises = exercises;
    }
}
