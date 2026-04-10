package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;

@ProjectedPayload
public interface TournamentChallengeRecordView extends AuditableView {
    BigInteger getId();
    BigInteger getEventGameId();
    String getParticipantId();
    TournamentParticipantType getParticipantType();
    String getContributorId();
    Integer getCycleNumber();
    Integer getSegmentNumber();
    Integer getStageNumber();
    BigInteger getGroupId();
    TournamentChallengeStatus getStatus();
    Instant getStartTime();
    Instant getEndTime();
    BigDecimal getBaseScore();
    BigDecimal getFinalScore();
    Map<String, Object> getExerciseDetails();
}
