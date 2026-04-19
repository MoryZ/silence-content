package com.old.silence.content.infrastructure.persistence.tournament;

import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.model.TournamentChallengeRecord;
import com.old.silence.content.domain.repository.tournament.TournamentChallengeRepository;
import com.old.silence.content.infrastructure.persistence.tournament.dao.TournamentChallengeDao;

import java.math.BigInteger;
import java.util.List;

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
    public List<TournamentChallengeRecord> findByEventGameIdAndCycleNumberAndGroupIdAndParticipantTypeAndStatus(BigInteger eventGameId, Integer cycleNumber, BigInteger groupId, TournamentParticipantType participantType, TournamentChallengeStatus status) {
        return tournamentChallengeDao.findByEventGameIdAndCycleNumberAndGroupIdAndParticipantTypeAndStatus(eventGameId, cycleNumber, groupId, participantType, status);
    }
}
