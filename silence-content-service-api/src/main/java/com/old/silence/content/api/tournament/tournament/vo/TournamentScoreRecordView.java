package com.old.silence.content.api.tournament.tournament.vo;

import org.springframework.data.web.ProjectedPayload;


import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentScoreType;

import java.math.BigDecimal;
import java.math.BigInteger;

@ProjectedPayload
public interface TournamentScoreRecordView {

    BigInteger getEventGameId();

    BigInteger getParticipantId();

    TournamentParticipantType getParticipantType();


    TournamentScoreType getScoreType();

    /**
     * 周期号
     */
    Integer getCycleNumber();

    /**
     * 片号
     */
    Integer getSegmentNumber();

    /**
     * 场次号
     */
    Integer getStageNumber();

    /**
     * 分组ID
     */
    BigInteger getGroupId();


    /**
     * 得分
     */
    BigDecimal getScore();

}
