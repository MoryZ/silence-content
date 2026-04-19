package com.old.silence.content.api.tournament.tournament.dto;

import org.springframework.data.repository.query.parser.Part;

import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;
import java.time.Instant;

/**
 * TournamentConfig查询对象
 */
public class TournamentConfigQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger eventGameId;

    @RelationalQueryProperty(name = "tournamentStartTime", type = Part.Type.GREATER_THAN_EQUAL)
    private Instant tournamentStartTimeFrom;

    public BigInteger getEventGameId() {
        return eventGameId;
    }

    public void setEventGameId(BigInteger eventGameId) {
        this.eventGameId = eventGameId;
    }

    public Instant getTournamentStartTimeFrom() {
        return tournamentStartTimeFrom;
    }

    public void setTournamentStartTimeFrom(Instant tournamentStartTimeFrom) {
        this.tournamentStartTimeFrom = tournamentStartTimeFrom;
    }

}
