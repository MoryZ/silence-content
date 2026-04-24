package com.old.silence.content.infrastructure.persistence.tournament.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.model.TournamentChallengeRecord;
import com.old.silence.data.jdbc.repository.JdbcCrudRepository;

/**
 * @author moryzang
 */
public interface TournamentChallengeDao extends JdbcCrudRepository<TournamentChallengeRecord, BigInteger> {

    int countByEventGameIdAndParticipantIdAndCycleNumberAndSegmentNumberAndStageNumber(BigInteger eventGameId,
                                                                                       BigInteger participantId, int cycleNumber, int segmentNumber, int stageNumber);


    List<TournamentChallengeRecord> findByEventGameIdAndCycleNumberAndParticipantIdInAndParticipantTypeAndStatus(BigInteger eventGameId, Integer cycleNumber, List<BigInteger> participantIds, TournamentParticipantType participantType, TournamentChallengeStatus status);

    <T> Optional<T> findFirstByEventGameIdAndCycleNumberAndGroupIdAndFinalScoreGreaterThanOrderByFinalScoreAsc(BigInteger eventGameId, Integer cycleNumber, BigInteger groupId, BigDecimal finalScore,
                                                                                                               Class<T> projectionType);
}
