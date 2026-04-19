package com.old.silence.content.api.tournament.tournament.dto;

import org.springframework.data.repository.query.parser.Part;


import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentScoreType;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

/**
 * @author EX-ZHANGMENGWEI001
 */
public class TournamentScoreRecordQuery {

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger eventGameId;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TournamentParticipantType participantType;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger participantId;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TournamentScoreType scoreType;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Integer stageNumber;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Integer segmentNumber;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Integer cycleNumber;

    @RelationalQueryProperty(type = Part.Type.GREATER_THAN)
    private BigInteger id;

    public BigInteger getEventGameId() {
        return eventGameId;
    }

    public void setEventGameId(BigInteger eventGameId) {
        this.eventGameId = eventGameId;
    }

    public TournamentParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(TournamentParticipantType participantType) {
        this.participantType = participantType;
    }

    public BigInteger getParticipantId() {
        return participantId;
    }

    public void setParticipantId(BigInteger participantId) {
        this.participantId = participantId;
    }

    public TournamentScoreType getScoreType() {
        return scoreType;
    }

    public void setScoreType(TournamentScoreType scoreType) {
        this.scoreType = scoreType;
    }

    public Integer getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(Integer stageNumber) {
        this.stageNumber = stageNumber;
    }

    public Integer getSegmentNumber() {
        return segmentNumber;
    }

    public void setSegmentNumber(Integer segmentNumber) {
        this.segmentNumber = segmentNumber;
    }

    public Integer getCycleNumber() {
        return cycleNumber;
    }

    public void setCycleNumber(Integer cycleNumber) {
        this.cycleNumber = cycleNumber;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
}
