package com.old.silence.content.api.tournament.tournament.vo;

import org.springframework.data.web.ProjectedPayload;

import com.old.silence.content.domain.enums.tournament.TournamentStageType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.time.LocalDate;

/**
 * TournamentGroup视图接口
 */
@ProjectedPayload
public interface TournamentGroupView extends AuditableView {

    BigInteger getId();

    BigInteger getEventGameId();

    TournamentStageType getStageType();

    Integer getStageNumber();

    LocalDate getGroupDate();

    Integer getGroupNumber();

    String getTierName();

    Integer getTierOrder();
}
