package com.old.silence.content.domain.repository.tournament;


import com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.model.TournamentChallengeRecord;

import java.math.BigInteger;
import java.util.List;

/**
 * @author moryzang
 */
public interface TournamentChallengeRepository {
    int create(TournamentChallengeRecord tournamentChallengeRecord);

    int countByEventGameIdAndParticipantIdAndCycleNumberAndSegmentNumberAndStageNumber(BigInteger eventGameId, BigInteger participantId, int cycleNumber, int segmentNumber, int stageNumber);

    List<TournamentChallengeRecord> findByEventGameIdAndCycleNumberAndGroupIdAndParticipantTypeAndStatus(BigInteger eventGameId, Integer cycleNumber, BigInteger groupId, TournamentParticipantType participantType, TournamentChallengeStatus status);
}
