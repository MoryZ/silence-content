package com.old.silence.content.infrastructure.persistence.tournament;

import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.model.TournamentChallengeRecord;
import com.old.silence.content.domain.repository.tournament.TournamentChallengeRepository;
import com.old.silence.content.infrastructure.persistence.tournament.dao.TournamentChallengeDao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * @author moryzang
 */
@Repository
public class TournamentChallengeMyBatisRepository implements TournamentChallengeRepository {

    private final TournamentChallengeDao tournamentChallengeDao;

    public TournamentChallengeMyBatisRepository(TournamentChallengeDao tournamentChallengeDao) {
        this.tournamentChallengeDao = tournamentChallengeDao;
    }
    @Override
    public int create(TournamentChallengeRecord tournamentChallengeRecord) {
        return tournamentChallengeDao.insert(tournamentChallengeRecord);
    }

    @Override
    public int countByEventGameIdAndParticipantIdAndCycleNumberAndSegmentNumberAndStageNumber(BigInteger eventGameId,
            BigInteger participantId, int cycleNumber, int segmentNumber, int stageNumber) {
        return tournamentChallengeDao.countByEventGameIdAndParticipantIdAndCycleNumberAndSegmentNumberAndStageNumber(eventGameId, participantId, cycleNumber, segmentNumber, stageNumber);
    }

    @Override
    public List<TournamentChallengeRecord> findByEventGameIdAndCycleNumberAndParticipantIdInAndParticipantTypeAndStatus(BigInteger eventGameId, Integer cycleNumber, List<BigInteger> participantIds, TournamentParticipantType participantType, TournamentChallengeStatus status) {
        return tournamentChallengeDao.findByEventGameIdAndCycleNumberAndParticipantIdInAndParticipantTypeAndStatus(eventGameId, cycleNumber, participantIds, participantType, status);
    }

    public <T> Optional<T> findFirstByEventGameIdAndCycleNumberAndGroupIdAndFinalScoreGreaterThanOrderByFinalScoreAsc(BigInteger eventGameId, Integer cycleNumber, BigInteger groupId, BigDecimal finalScore, Class<T> projectionType) {
        return tournamentChallengeDao.findFirstByEventGameIdAndCycleNumberAndGroupIdAndFinalScoreGreaterThanOrderByFinalScoreAsc(eventGameId, cycleNumber, groupId, finalScore, projectionType);
    }
}
