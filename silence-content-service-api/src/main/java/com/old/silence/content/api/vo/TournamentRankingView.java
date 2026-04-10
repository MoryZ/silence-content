package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentRankingType;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigDecimal;
import java.math.BigInteger;

@ProjectedPayload
public interface TournamentRankingView extends AuditableView {
    BigInteger getId();
    BigInteger getEventGameId();
    BigInteger getGroupId();
    String getParticipantId();
    TournamentParticipantType getParticipantType();
    TournamentRankingType getRankingType();
    BigDecimal getScore();
    Integer getRankNo();
    String getAvatarUrl();
    String getNickname();
}
