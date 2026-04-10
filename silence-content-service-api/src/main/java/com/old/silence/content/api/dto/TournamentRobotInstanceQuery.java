package com.old.silence.content.api.dto;

import org.springframework.data.repository.query.parser.Part;
import com.old.silence.content.domain.enums.tournament.TournamentRobotStatus;
import com.old.silence.data.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;

public class TournamentRobotInstanceQuery {
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger eventGameId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private BigInteger templateId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private String robotId;
    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
    private TournamentRobotStatus status;

    public BigInteger getEventGameId() { return eventGameId; }
    public void setEventGameId(BigInteger eventGameId) { this.eventGameId = eventGameId; }
    public BigInteger getTemplateId() { return templateId; }
    public void setTemplateId(BigInteger templateId) { this.templateId = templateId; }
    public String getRobotId() { return robotId; }
    public void setRobotId(String robotId) { this.robotId = robotId; }
    public TournamentRobotStatus getStatus() { return status; }
    public void setStatus(TournamentRobotStatus status) { this.status = status; }
}
