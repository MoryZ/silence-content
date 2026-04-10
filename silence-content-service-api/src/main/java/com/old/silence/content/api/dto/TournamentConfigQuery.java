package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.tournament.TournamentCurrentStage;
import com.old.silence.content.domain.enums.tournament.TournamentDivisionMode;
import com.old.silence.content.domain.enums.tournament.TournamentMatchMode;
import com.old.silence.content.domain.enums.tournament.TournamentType;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

/**
 * TournamentConfig查询对象
 */
public class TournamentConfigQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger eventGameId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TournamentType tournamentType;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TournamentDivisionMode divisionMode;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TournamentMatchMode matchMode;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TournamentCurrentStage currentStage;

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

    public TournamentCurrentStage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(TournamentCurrentStage currentStage) {
        this.currentStage = currentStage;
    }
}