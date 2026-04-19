package com.old.silence.content.api.tournament.tournament.dto.support;



import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.old.silence.content.domain.enums.ExerciseCoefficientPickMode;
import com.old.silence.content.domain.enums.tournament.TournamentStageType;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author EX-ZHANGMENGWEI001
 */
public class CycleStageConfig {

    private TournamentStageType stageType;
    @NotNull
    private ExerciseCoefficientPickMode pickMode;
    @NotEmpty
    @Size(min = 3)
    private List<@Valid ExerciseScoreCoefficient> exerciseScoreCoefficients;

    @NotNull
    private BigDecimal robotScoreCoefficientMin;
    @NotNull
    private BigDecimal robotScoreCoefficientMax;
    @NotEmpty
    private List<@Valid TierDefinition> cycleTierDefinitions;
    @NotEmpty
    @Size(max = 10)
    private List<@Valid TierDefinition> tournamentTierDefinitions;

    @Valid
    private ShareConfig shareConfig;

    private CycleConfig cycleConfig;

    private SegmentConfig segmentConfig;

    @NotEmpty
    private List<@Valid StageConfig> stageConfigs;

    public TournamentStageType getStageType() {
        return stageType;
    }

    public void setStageType(TournamentStageType stageType) {
        this.stageType = stageType;
    }

    public ExerciseCoefficientPickMode getPickMode() {
        return pickMode;
    }

    public void setPickMode(ExerciseCoefficientPickMode pickMode) {
        this.pickMode = pickMode;
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

    public BigDecimal getRobotScoreCoefficientMax() {
        return robotScoreCoefficientMax;
    }

    public void setRobotScoreCoefficientMax(BigDecimal robotScoreCoefficientMax) {
        this.robotScoreCoefficientMax = robotScoreCoefficientMax;
    }

    public List<TierDefinition> getCycleTierDefinitions() {
        return cycleTierDefinitions;
    }

    public void setCycleTierDefinitions(List<TierDefinition> cycleTierDefinitions) {
        this.cycleTierDefinitions = cycleTierDefinitions;
    }

    public List<TierDefinition> getTournamentTierDefinitions() {
        return tournamentTierDefinitions;
    }

    public void setTournamentTierDefinitions(List<TierDefinition> tournamentTierDefinitions) {
        this.tournamentTierDefinitions = tournamentTierDefinitions;
    }

    public ShareConfig shareConfig() {
        return shareConfig;
    }

    public void setShareConfig(ShareConfig shareConfig) {
        this.shareConfig = shareConfig;
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
