package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.tournament.TournamentRobotInstanceType;
import com.old.silence.content.domain.enums.tournament.TournamentRobotStatus;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

@ProjectedPayload
public interface TournamentRobotInstanceView extends AuditableView {
    BigInteger getId();
    BigInteger getEventGameId();
    BigInteger getTemplateId();
    TournamentRobotInstanceType getRobotInstanceType();
    String getRobotId();
    String getNickname();
    String getAvatarUrl();
    TournamentRobotStatus getStatus();
}
