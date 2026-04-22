package com.old.silence.content.console.dto;


import com.old.silence.content.api.tournament.tournament.dto.support.CycleStageConfig;
import com.old.silence.content.domain.enums.tournament.TournamentDivisionMode;
import com.old.silence.content.domain.enums.tournament.TournamentMatchMode;
import com.old.silence.content.domain.enums.tournament.TournamentRobotMode;
import com.old.silence.content.domain.enums.tournament.TournamentType;
import com.old.silence.validation.group.UpdateValidation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

/**
 * @author EX-ZHANGMENGWEI001
 */
public class TournamentConfigConsoleCommand {

    /**
     * 赛事类型
     */
    @NotNull(groups = UpdateValidation.class)
    private BigInteger eventGameId;

    /**
     * 赛事类型
     */
    @NotNull
    private TournamentType tournamentType;


    /**
     * 赛区模式
     */
    @NotNull
    private TournamentDivisionMode divisionMode;

    /**
     * 匹配模式
     */
    @NotNull
    private TournamentMatchMode matchMode;

    /**
     * 机器人
     */
    @NotNull
    private TournamentRobotMode robotMode;

    /**
     * 报名开始时间
     */
    @NotNull
    private Instant registrationStartTime;

    /**
     * 报名结束时间
     */
    @NotNull
    private Instant registrationEndTime;

    /**
     * 赛事开始时间
     */
    @NotNull
    private Instant tournamentStartTime;

    /**
     * 赛事结束时间
     */
    @NotNull
    private Instant tournamentEndTime;

    /**
     * 最小报名人数
     */
    @NotNull
    @Positive
    @Min(10)
    private Integer minParticipants;

    /**
     * 最大报名人数
     */
    @NotNull
    @Positive
    @Max(99999)
    private Integer maxParticipants;

    @NotNull
    @Positive
    @Max(50)
    private Integer groupSize;

    /**
     * 每日最大挑战次数
     */
    private Integer maxDailyChallenges;

    /**
     * 挑战超时时间（分钟）
     */
    private Integer challengeTimeoutMinutes;

    /**
     * 活动信息
     */
    @NotNull
    @Valid
    private MarketingEventConsoleCommand marketingEvent;

    @NotEmpty
    @Size(max = 20)
    private List<@Valid EventGameRewardItemAndRuleConsoleCommand> eventGameRewardItems;

    /**
     * 赛事配置
     */
    @Valid
    private CycleStageConfig cycleStageConfig;

    public BigInteger getEventGameId() {
        return eventGameId;
    }

    public void setEventGameId(BigInteger eventGameId) {
        this.eventGameId = eventGameId;
    }

    public TournamentType getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(TournamentType tournamentType) {
        this.tournamentType = tournamentType;
    }

    public TournamentDivisionMode getDivisionMode() {
        return divisionMode;
    }

    public void setDivisionMode(TournamentDivisionMode divisionMode) {
        this.divisionMode = divisionMode;
    }

    public TournamentMatchMode getMatchMode() {
        return matchMode;
    }

    public void setMatchMode(TournamentMatchMode matchMode) {
        this.matchMode = matchMode;
    }

    public TournamentRobotMode getRobotMode() {
        return robotMode;
    }

    public void setRobotMode(TournamentRobotMode robotMode) {
        this.robotMode = robotMode;
    }

    public Instant getRegistrationStartTime() {
        return registrationStartTime;
    }

    public void setRegistrationStartTime(Instant registrationStartTime) {
        this.registrationStartTime = registrationStartTime;
    }

    public Instant getRegistrationEndTime() {
        return registrationEndTime;
    }

    public void setRegistrationEndTime(Instant registrationEndTime) {
        this.registrationEndTime = registrationEndTime;
    }

    public Instant getTournamentStartTime() {
        return tournamentStartTime;
    }

    public void setTournamentStartTime(Instant tournamentStartTime) {
        this.tournamentStartTime = tournamentStartTime;
    }

    public Instant getTournamentEndTime() {
        return tournamentEndTime;
    }

    public void setTournamentEndTime(Instant tournamentEndTime) {
        this.tournamentEndTime = tournamentEndTime;
    }

    public Integer getMinParticipants() {
        return minParticipants;
    }

    public void setMinParticipants(Integer minParticipants) {
        this.minParticipants = minParticipants;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
    }

    public Integer getMaxDailyChallenges() {
        return maxDailyChallenges;
    }

    public void setMaxDailyChallenges(Integer maxDailyChallenges) {
        this.maxDailyChallenges = maxDailyChallenges;
    }

    public Integer getChallengeTimeoutMinutes() {
        return challengeTimeoutMinutes;
    }

    public void setChallengeTimeoutMinutes(Integer challengeTimeoutMinutes) {
        this.challengeTimeoutMinutes = challengeTimeoutMinutes;
    }

    public MarketingEventConsoleCommand getMarketingEvent() {
        return marketingEvent;
    }

    public void setMarketingEvent(MarketingEventConsoleCommand marketingEvent) {
        this.marketingEvent = marketingEvent;
    }

    public List<EventGameRewardItemAndRuleConsoleCommand> getEventGameRewardItems() {
        return eventGameRewardItems;
    }

    public void setEventGameRewardItems(List<EventGameRewardItemAndRuleConsoleCommand> eventGameRewardItems) {
        this.eventGameRewardItems = eventGameRewardItems;
    }

    public CycleStageConfig getCycleStageConfig() {
        return cycleStageConfig;
    }

    public void setCycleStageConfig(CycleStageConfig cycleStageConfig) {
        this.cycleStageConfig = cycleStageConfig;
    }
}
