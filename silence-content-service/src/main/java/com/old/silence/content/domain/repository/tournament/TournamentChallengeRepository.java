package com.old.silence.content.domain.repository.tournament;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.model.TournamentChallengeRecord;

/**
 * @author moryzang
 */
public interface TournamentChallengeRepository {
    int create(TournamentChallengeRecord tournamentChallengeRecord);

    int countByEventGameIdAndParticipantIdAndCycleNumberAndSegmentNumberAndStageNumber(BigInteger eventGameId, BigInteger participantId, int cycleNumber, int segmentNumber, int stageNumber);

    List<TournamentChallengeRecord> findByEventGameIdAndCycleNumberAndParticipantIdInAndParticipantTypeAndStatus(BigInteger eventGameId, Integer cycleNumber, List<BigInteger> participantIds, TournamentParticipantType participantType,
                                                                                                                 TournamentChallengeStatus status);

    <T> Optional<T> findFirstByEventGameIdAndCycleNumberAndGroupIdAndFinalScoreGreaterThanOrderByFinalScoreAsc(BigInteger eventGameId, Integer cycleNumber, BigInteger groupId, BigDecimal finalScore,
                                                                                                               Class<T> projectionType);
}
