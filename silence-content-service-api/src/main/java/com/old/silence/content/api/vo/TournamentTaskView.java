package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;
import java.time.Instant;

/**
 * TournamentTask视图接口
 */
@ProjectedPayload
public interface TournamentTaskView extends AuditableView {

    BigInteger getId();

    BigInteger getTournamentId();

    BigInteger getEventGameId();

    TournamentTaskType getTaskType();

    Integer getStageNo();

    Integer getSegmentNo();

    Integer getCycleNo();

    Instant getTriggerTime();

    TournamentTaskStatus getStatus();

    Integer getRetryCount();

    BigInteger getDependsOnTaskId();

    TournamentTaskStatus getDependsOnStatus();

    String getRunTraceId();
}