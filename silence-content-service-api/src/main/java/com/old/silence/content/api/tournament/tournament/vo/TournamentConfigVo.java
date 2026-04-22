package com.old.silence.content.api.tournament.tournament.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.old.silence.content.domain.enums.tournament.TournamentDivisionMode;
import com.old.silence.content.domain.enums.tournament.TournamentMatchMode;
import com.old.silence.content.domain.enums.tournament.TournamentRobotMode;
import com.old.silence.content.domain.enums.tournament.TournamentType;
import com.old.silence.json.JacksonMapper;


import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class TournamentConfigVo {

    /**
     * 赛事ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigInteger id;

    /**
     * 玩法ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
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
     * 比赛开始时间
     */
    private Instant tournamentStartTime;

    /**
     * 比赛结束时间
     */
    private Instant tournamentEndTime;

    /**
     * maxParticipants
     */
    private Integer maxParticipants;

    /**
     * 最大报名人数
     */
    private Integer minParticipants;

    /**
     * 每组人数
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

    private Map<String, Object> attributes;

    private String version;

    //活动信息
    TournamentMarketingEventVo event;

    //赛事奖励
    List<TournamentRewardItemVo> tournamentRewards;

    //报名人数
    private Integer registrationCount;

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

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Integer getMinParticipants() {
        return minParticipants;
    }

    public void setMinParticipants(Integer minParticipants) {
        this.minParticipants = minParticipants;
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

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public TournamentMarketingEventVo getEvent() {
        return event;
    }

    public void setEvent(TournamentMarketingEventVo event) {
        this.event = event;
    }

    public List<TournamentRewardItemVo> getTournamentRewards() {
        return tournamentRewards;
    }

    public void setTournamentRewards(List<TournamentRewardItemVo> tournamentRewards) {
        this.tournamentRewards = tournamentRewards;
    }

    public Integer getRegistrationCount() {
        return registrationCount;
    }

    public void setRegistrationCount(Integer registrationCount) {
        this.registrationCount = registrationCount;
    }

    public <T> T converterAttributes(Class<T> type) {
        if (attributes == null) {
            return null;
        }

        return JacksonMapper.getSharedInstance().fromJson(
                JacksonMapper.getSharedInstance().toJson(attributes),
                type
        );
    }
}
