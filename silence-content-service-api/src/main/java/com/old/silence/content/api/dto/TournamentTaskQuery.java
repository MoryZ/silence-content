package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

/**
 * TournamentTask查询对象
 */
public class TournamentTaskQuery {

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger tournamentId;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger eventGameId;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TournamentTaskType taskType;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TournamentTaskStatus status;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Integer stageNo;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Integer segmentNo;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Integer cycleNo;

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private String runTraceId;

    public BigInteger getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(BigInteger tournamentId) {
        this.tournamentId = tournamentId;
    }

    public BigInteger getEventGameId() {
        return eventGameId;
    }

    public void setEventGameId(BigInteger eventGameId) {
        this.eventGameId = eventGameId;
    }

    public TournamentTaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TournamentTaskType taskType) {
        this.taskType = taskType;
    }

    public TournamentTaskStatus getStatus() {
        return status;
    }

    public void setStatus(TournamentTaskStatus status) {
        this.status = status;
    }

    public Integer getStageNo() {
        return stageNo;
    }

    public void setStageNo(Integer stageNo) {
        this.stageNo = stageNo;
    }

    public Integer getSegmentNo() {
        return segmentNo;
    }

    public void setSegmentNo(Integer segmentNo) {
        this.segmentNo = segmentNo;
    }

    public Integer getCycleNo() {
        return cycleNo;
    }

    public void setCycleNo(Integer cycleNo) {
        this.cycleNo = cycleNo;
    }

    public String getRunTraceId() {
        return runTraceId;
    }

    public void setRunTraceId(String runTraceId) {
        this.runTraceId = runTraceId;
    }
}