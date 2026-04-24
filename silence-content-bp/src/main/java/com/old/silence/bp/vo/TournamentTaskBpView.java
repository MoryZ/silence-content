package com.old.silence.bp.vo;


import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;

import java.math.BigInteger;

public interface TournamentTaskBpView {

    BigInteger getId();

    TournamentTaskType getTaskType();

    BigInteger getEventGameId();

    Integer getCycleNo();

    BigInteger getTournamentId();

    TournamentTaskStatus getStatus();

    Integer getRetryCount();

    BigInteger getDependsOnTaskId();

}
