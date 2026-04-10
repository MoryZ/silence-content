package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.old.silence.content.domain.enums.tournament.TournamentRobotInstanceType;
import com.old.silence.content.domain.enums.tournament.TournamentRobotStatus;

import java.math.BigInteger;

public class TournamentRobotInstanceCommand {
    @NotNull
    private BigInteger eventGameId;
    @NotNull
    private BigInteger templateId;
    @NotNull
    private TournamentRobotInstanceType robotInstanceType;
    @NotBlank
    private String robotId;
    private String nickname;
    private String avatarUrl;
    @NotNull
    private TournamentRobotStatus status;

    public BigInteger getEventGameId() { return eventGameId; }
    public void setEventGameId(BigInteger eventGameId) { this.eventGameId = eventGameId; }
    public BigInteger getTemplateId() { return templateId; }
    public void setTemplateId(BigInteger templateId) { this.templateId = templateId; }
    public TournamentRobotInstanceType getRobotInstanceType() { return robotInstanceType; }
    public void setRobotInstanceType(TournamentRobotInstanceType robotInstanceType) { this.robotInstanceType = robotInstanceType; }
    public String getRobotId() { return robotId; }
    public void setRobotId(String robotId) { this.robotId = robotId; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public TournamentRobotStatus getStatus() { return status; }
    public void setStatus(TournamentRobotStatus status) { this.status = status; }
}
