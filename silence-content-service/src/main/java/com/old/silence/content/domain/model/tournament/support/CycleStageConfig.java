package com.old.silence.content.domain.model.tournament.support;

import java.math.BigDecimal;
import java.util.List;

import com.old.silence.content.domain.enums.tournament.TournamentStageType;


/**
 * @author moryzang
 */
public class CycleStageConfig {

    private TournamentStageType stageType;
    private List<ExerciseScoreCoefficient> exerciseScoreCoefficients;
    private BigDecimal robotScoreCoefficientMin;
    private BigDecimal robotSpeedCoefficientMax;
    private List<TierMapping> tierMapping;
    private List<TierDefinition> tierDefinitions;

    private CycleConfig cycleConfig;

    private SegmentConfig segmentConfig;

    private List<StageConfig> stageConfigs;

    public TournamentStageType getStageType() {
        return stageType;
    }

    public void setStageType(TournamentStageType stageType) {
        this.stageType = stageType;
    }

    public List<ExerciseScoreCoefficient> getExerciseScoreCoefficients() {
        return exerciseScoreCoefficients;
    }

    public void setExerciseScoreCoefficients(List<ExerciseScoreCoefficient> exerciseScoreCoefficients) {
        this.exerciseScoreCoefficients = exerciseScoreCoefficients;
    }

    public BigDecimal getRobotScoreCoefficientMin() {
        return robotScoreCoefficientMin;
    }

    public void setRobotScoreCoefficientMin(BigDecimal robotScoreCoefficientMin) {
        this.robotScoreCoefficientMin = robotScoreCoefficientMin;
    }

    public BigDecimal getRobotSpeedCoefficientMax() {
        return robotSpeedCoefficientMax;
    }

    public void setRobotSpeedCoefficientMax(BigDecimal robotSpeedCoefficientMax) {
        this.robotSpeedCoefficientMax = robotSpeedCoefficientMax;
    }

    public List<TierMapping> getTierMapping() {
        return tierMapping;
    }

    public void setTierMapping(List<TierMapping> tierMapping) {
        this.tierMapping = tierMapping;
    }

    public List<TierDefinition> getTierDefinitions() {
        return tierDefinitions;
    }

    public void setTierDefinitions(List<TierDefinition> tierDefinitions) {
        this.tierDefinitions = tierDefinitions;
    }

    public CycleConfig getCycleConfig() {
        return cycleConfig;
    }

    public void setCycleConfig(CycleConfig cycleConfig) {
        this.cycleConfig = cycleConfig;
    }

    public SegmentConfig getSegmentConfig() {
        return segmentConfig;
    }

    public void setSegmentConfig(SegmentConfig segmentConfig) {
        this.segmentConfig = segmentConfig;
    }

    public List<StageConfig> getStageConfigs() {
        return stageConfigs;
    }

    public void setStageConfigs(List<StageConfig> stageConfigs) {
        this.stageConfigs = stageConfigs;
    }
}
