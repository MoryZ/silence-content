package com.old.silence.content.api.tournament.tournament.dto;

import org.springframework.data.repository.query.parser.Part;

import com.old.silence.content.domain.enums.tournament.TournamentStageType;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;
import java.time.LocalDate;

/**
 * TournamentGroup查询对象
 */
public class TournamentGroupQuery {

    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger eventGameId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TournamentStageType stageType;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private Integer stageNumber;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private LocalDate groupDate;

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
}
