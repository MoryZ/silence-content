package com.old.silence.content.api.tournament.tournament.dto;



import jakarta.validation.constraints.NotNull;
import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;

import java.math.BigInteger;
import java.time.Instant;

/**
 * @author EX-ZHANGMENGWEI001
 */
public class TournamentTaskDomainCommand {

    /**
     * 赛事配置ID
     */
    @NotNull
    private BigInteger tournamentId;

    /**
     * 玩法ID（event_game_id）
     */
    @NotNull
    private BigInteger eventGameId;

    /**
     * 任务类型
     */
    @NotNull
    private TournamentTaskType taskType;

    /**
     * 场次号（用于场次结算任务）
     */
    private Integer stageNo;

    /**
     * 片号（用于片结算任务）
     */
    private Integer segmentNo;

    /**
     * 周期号（用于周期结算任务）
     */
    private Integer cycleNo;

    /**
     * 任务触发时间
     */
    @NotNull
    private Instant triggerTime;

    /**
     * 任务状态
     */
    @NotNull
    private TournamentTaskStatus status;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 前置任务ID
     */
    private BigInteger dependsOnTaskId;

    /**
     * 前置任务需要达到的状态
     */
    private TournamentTaskStatus dependsOnStatus;

    /**
     * 任务执行链路追踪ID
     */
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

    public Instant getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(Instant triggerTime) {
        this.triggerTime = triggerTime;
    }

    public TournamentTaskStatus getStatus() {
        return status;
    }

    public void setStatus(TournamentTaskStatus status) {
        this.status = status;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public BigInteger getDependsOnTaskId() {
        return dependsOnTaskId;
    }

    public void setDependsOnTaskId(BigInteger dependsOnTaskId) {
        this.dependsOnTaskId = dependsOnTaskId;
    }

    public TournamentTaskStatus getDependsOnStatus() {
        return dependsOnStatus;
    }

    public void setDependsOnStatus(TournamentTaskStatus dependsOnStatus) {
        this.dependsOnStatus = dependsOnStatus;
    }

    public String getRunTraceId() {
        return runTraceId;
    }

    public void setRunTraceId(String runTraceId) {
        this.runTraceId = runTraceId;
    }
}
