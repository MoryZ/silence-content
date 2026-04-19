package com.old.silence.content.infrastructure.persistence.tournament.dao;

import org.apache.ibatis.annotations.Mapper;
import com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.data.jdbc.repository.JdbcCrudRepository;
import com.old.silence.content.domain.model.TournamentChallengeRecord;

import java.math.BigInteger;
import java.util.List;

/**
 * @author moryzang
 */
@Mapper
public interface TournamentChallengeDao extends JdbcCrudRepository<TournamentChallengeRecord, BigInteger> {

    int countByEventGameIdAndParticipantIdAndCycleNumberAndSegmentNumberAndStageNumber(BigInteger eventGameId,
            BigInteger participantId, int cycleNumber, int segmentNumber, int stageNumber);


    List<TournamentChallengeRecord> findByEventGameIdAndCycleNumberAndGroupIdAndParticipantTypeAndStatus(BigInteger eventGameId, Integer cycleNumber, BigInteger groupId, TournamentParticipantType participantType, TournamentChallengeStatus status);

}
