package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotNull;
import com.old.silence.content.domain.enums.tournament.TournamentStageType;

import java.math.BigInteger;
import java.time.LocalDate;

/**
 * TournamentGroup命令对象
 */
public class TournamentGroupCommand {

    @NotNull
    private BigInteger eventGameId;
    @NotNull
    private TournamentStageType stageType;
    @NotNull
    private Integer stageNumber;
    @NotNull
    private LocalDate groupDate;
    @NotNull
    private Integer groupNumber;
    private String tierName;
    private Integer tierOrder;

    public BigInteger getEventGameId() {
        return eventGameId;
    }

    public void setEventGameId(BigInteger eventGameId) {
        this.eventGameId = eventGameId;
    }

    public TournamentStageType getStageType() {
        return stageType;
    }

    public void setStageType(TournamentStageType stageType) {
        this.stageType = stageType;
    }

    public Integer getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(Integer stageNumber) {
        this.stageNumber = stageNumber;
    }

    public LocalDate getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(LocalDate groupDate) {
        this.groupDate = groupDate;
    }

    public Integer getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getTierName() {
        return tierName;
    }

    public void setTierName(String tierName) {
        this.tierName = tierName;
    }

    public Integer getTierOrder() {
        return tierOrder;
    }

    public void setTierOrder(Integer tierOrder) {
        this.tierOrder = tierOrder;
    }
}
