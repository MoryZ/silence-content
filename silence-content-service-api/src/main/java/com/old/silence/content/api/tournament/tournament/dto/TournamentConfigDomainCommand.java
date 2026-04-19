package com.old.silence.content.api.tournament.tournament.dto;



import com.old.silence.content.api.dto.EventGameRewardItemDomainCommand;
import com.old.silence.content.api.dto.MarketingEventDomainCommand;
import com.old.silence.content.domain.enums.tournament.TournamentDivisionMode;
import com.old.silence.content.domain.enums.tournament.TournamentMatchMode;
import com.old.silence.content.domain.enums.tournament.TournamentRobotMode;
import com.old.silence.content.domain.enums.tournament.TournamentType;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * @author EX-ZHANGMENGWEI001
 */
public class TournamentConfigDomainCommand {

    private BigInteger id;

    private BigInteger eventGameId;

    /**
     * 赛事类型
     */
    private TournamentType tournamentType;

    /**
     * 赛区模式
     */
    private TournamentDivisionMode divisionMode;

    /**
     * 匹配模式
     */
    private TournamentMatchMode matchMode;

    /**
     * 机器人
     */
    private TournamentRobotMode robotMode;

    /**
     * 报名开始时间
     */
    private Instant registrationStartTime;

    /**
     * 报名结束时间
     */
    private Instant registrationEndTime;

    /**
     * 赛事开始时间
     */
    private Instant tournamentStartTime;

    /**
     * 赛事结束时间
     */
    private Instant tournamentEndTime;

    /**
     * 最小报名人数
     */
    private Integer minParticipants;

    /**
     * 最大报名人数
     */
    private Integer maxParticipants;

    /**
     * 最大小组数量
     */
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
    private MarketingEventDomainCommand marketingEvent;

    private List<EventGameRewardItemDomainCommand> eventGameRewardItems;


    /**
     * 赛事配置
     */
    private Map<String, Object> attributes;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

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

    public MarketingEventDomainCommand getMarketingEvent() {
        return marketingEvent;
    }

    public void setMarketingEvent(MarketingEventDomainCommand marketingEvent) {
        this.marketingEvent = marketingEvent;
    }

    public List<EventGameRewardItemDomainCommand> getEventGameRewardItems() {
        return eventGameRewardItems;
    }

    public void setEventGameRewardItems(List<EventGameRewardItemDomainCommand> eventGameRewardItems) {
        this.eventGameRewardItems = eventGameRewardItems;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
