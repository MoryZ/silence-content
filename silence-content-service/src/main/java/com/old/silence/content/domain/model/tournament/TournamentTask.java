package com.old.silence.content.domain.model.tournament;

import org.springframework.data.annotation.Id;
import com.old.silence.content.domain.enums.tournament.TaskStatusEnum;
import com.old.silence.content.domain.enums.tournament.TaskTypeEnum;

import java.math.BigInteger;
import java.time.Instant;

/**
 * 赛事自定义任务调度表
 *
 * @author moryzang
 */
public class TournamentTask {

    /**
     * 任务ID
     */
    @Id
    private BigInteger taskId;

    /**
     * 赛事ID
     */
    private BigInteger tournamentId;

    /**
     * 任务类型
     */
    private TaskTypeEnum taskType;

    /**
     * 期号
     */
    private Integer periodNo;

    /**
     * 任务触发时间
     */
    private Instant triggerTime;

    /**
     * 任务状态
     */
    private TaskStatusEnum status;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 创建时间
     */
    private Instant createTime;

    /**
     * 更新时间
     */
    private Instant updateTime;

    public BigInteger getTaskId() {
        return taskId;
    }

    public void setTaskId(BigInteger taskId) {
        this.taskId = taskId;
    }

    public BigInteger getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(BigInteger tournamentId) {
        this.tournamentId = tournamentId;
    }

    public TaskTypeEnum getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskTypeEnum taskType) {
        this.taskType = taskType;
    }

    public Integer getPeriodNo() {
        return periodNo;
    }

    public void setPeriodNo(Integer periodNo) {
        this.periodNo = periodNo;
    }

    public Instant getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(Instant triggerTime) {
        this.triggerTime = triggerTime;
    }

    public TaskStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TaskStatusEnum status) {
        this.status = status;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }
}
