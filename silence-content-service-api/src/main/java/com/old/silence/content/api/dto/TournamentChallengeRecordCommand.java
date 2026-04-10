package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;

public class TournamentChallengeRecordCommand {
    @NotNull
    private BigInteger eventGameId;
    @NotBlank
    private String participantId;
    @NotNull
    private TournamentParticipantType participantType;
    private String contributorId;
    private Integer cycleNumber;
    private Integer segmentNumber;
    private Integer stageNumber;
    private BigInteger groupId;
    @NotNull
    private TournamentChallengeStatus status;
    private Instant startTime;
    private Instant endTime;
    private BigDecimal baseScore;
    private BigDecimal finalScore;
    private Map<String, Object> exerciseDetails;

    public BigInteger getEventGameId() { return eventGameId; }
    public void setEventGameId(BigInteger eventGameId) { this.eventGameId = eventGameId; }
    public String getParticipantId() { return participantId; }
    public void setParticipantId(String participantId) { this.participantId = participantId; }
    public TournamentParticipantType getParticipantType() { return participantType; }
    public void setParticipantType(TournamentParticipantType participantType) { this.participantType = participantType; }
    public String getContributorId() { return contributorId; }
    public void setContributorId(String contributorId) { this.contributorId = contributorId; }
    public Integer getCycleNumber() { return cycleNumber; }
    public void setCycleNumber(Integer cycleNumber) { this.cycleNumber = cycleNumber; }
    public Integer getSegmentNumber() { return segmentNumber; }
    public void setSegmentNumber(Integer segmentNumber) { this.segmentNumber = segmentNumber; }
    public Integer getStageNumber() { return stageNumber; }
    public void setStageNumber(Integer stageNumber) { this.stageNumber = stageNumber; }
    public BigInteger getGroupId() { return groupId; }
    public void setGroupId(BigInteger groupId) { this.groupId = groupId; }
    public TournamentChallengeStatus getStatus() { return status; }
    public void setStatus(TournamentChallengeStatus status) { this.status = status; }
    public Instant getStartTime() { return startTime; }
    public void setStartTime(Instant startTime) { this.startTime = startTime; }
    public Instant getEndTime() { return endTime; }
    public void setEndTime(Instant endTime) { this.endTime = endTime; }
    public BigDecimal getBaseScore() { return baseScore; }
    public void setBaseScore(BigDecimal baseScore) { this.baseScore = baseScore; }
    public BigDecimal getFinalScore() { return finalScore; }
    public void setFinalScore(BigDecimal finalScore) { this.finalScore = finalScore; }
    public Map<String, Object> getExerciseDetails() { return exerciseDetails; }
    public void setExerciseDetails(Map<String, Object> exerciseDetails) { this.exerciseDetails = exerciseDetails; }
}
