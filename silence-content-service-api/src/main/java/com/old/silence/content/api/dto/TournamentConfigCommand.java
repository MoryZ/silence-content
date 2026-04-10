package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotNull;
import com.old.silence.content.domain.enums.tournament.TournamentCurrentStage;
import com.old.silence.content.domain.enums.tournament.TournamentDivisionMode;
import com.old.silence.content.domain.enums.tournament.TournamentMatchMode;
import com.old.silence.content.domain.enums.tournament.TournamentType;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;

/**
 * TournamentConfig命令对象
 */
public class TournamentConfigCommand {
    @NotNull
    private BigInteger eventGameId;
    @NotNull
    private TournamentType tournamentType;
    @NotNull
    private TournamentDivisionMode divisionMode;
    @NotNull
    private TournamentMatchMode matchMode;
    private Boolean robotEnabled;
    private TournamentCurrentStage currentStage;
    private Integer totalStages;
    private Integer stageDurationDays;
    private Instant registrationStartTime;
    private Instant registrationEndTime;
    private Instant tournamentStartTime;
    private Instant tournamentEndTime;
    private Integer maxParticipants;
    private Integer minParticipants;
    private Integer groupSize;
    private Integer maxDailyChallenges;
    private Integer challengeTimeoutMinutes;
    private Map<String, Object> attributes;

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

    public Boolean getRobotEnabled() {
        return robotEnabled;
    }

    public void setRobotEnabled(Boolean robotEnabled) {
        this.robotEnabled = robotEnabled;
    }

    public TournamentCurrentStage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(TournamentCurrentStage currentStage) {
        this.currentStage = currentStage;
    }

    public Integer getTotalStages() {
        return totalStages;
    }

    public void setTotalStages(Integer totalStages) {
        this.totalStages = totalStages;
    }

    public Integer getStageDurationDays() {
        return stageDurationDays;
    }

    public void setStageDurationDays(Integer stageDurationDays) {
        this.stageDurationDays = stageDurationDays;
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
}