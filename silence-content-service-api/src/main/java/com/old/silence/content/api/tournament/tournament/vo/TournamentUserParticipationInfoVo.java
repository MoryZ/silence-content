package com.old.silence.content.api.tournament.tournament.vo;


import java.math.BigInteger;
import java.time.Instant;

public class TournamentUserParticipationInfoVo {

    private BigInteger eventGameId;

    private Boolean registeredStatus;

    private Instant registrationTime;

    private Integer sessionChallengeStatus;

    private Integer sessionChallengeCount;

    private Integer maxSessionChallenges;

    private TournamentParticipantGroupVo currentGroup;

    private TournamentTierVo finalTier;

    public BigInteger getEventGameId() {
        return eventGameId;
    }

    public void setEventGameId(BigInteger eventGameId) {
        this.eventGameId = eventGameId;
    }

    public Boolean getRegisteredStatus() {
        return registeredStatus;
    }

    public void setRegisteredStatus(Boolean registeredStatus) {
        this.registeredStatus = registeredStatus;
    }

    public Instant getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Instant registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Integer getSessionChallengeStatus() {
        return sessionChallengeStatus;
    }

    public void setSessionChallengeStatus(Integer sessionChallengeStatus) {
        this.sessionChallengeStatus = sessionChallengeStatus;
    }

    public Integer getSessionChallengeCount() {
        return sessionChallengeCount;
    }

    public void setSessionChallengeCount(Integer sessionChallengeCount) {
        this.sessionChallengeCount = sessionChallengeCount;
    }

    public Integer getMaxSessionChallenges() {
        return maxSessionChallenges;
    }

    public void setMaxSessionChallenges(Integer maxSessionChallenges) {
        this.maxSessionChallenges = maxSessionChallenges;
    }

    public TournamentParticipantGroupVo getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(TournamentParticipantGroupVo currentGroup) {
        this.currentGroup = currentGroup;
    }

    public TournamentTierVo getFinalTier() {
        return finalTier;
    }

    public void setFinalTier(TournamentTierVo finalTier) {
        this.finalTier = finalTier;
    }
}
