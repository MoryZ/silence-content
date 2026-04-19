package com.old.silence.content.infrastructure.persistence.tournament;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.tournament.TournamentScoreType;
import com.old.silence.content.domain.model.TournamentScoreRecord;
import com.old.silence.content.domain.repository.tournament.TournamentScoreRecordRepository;
import com.old.silence.content.infrastructure.persistence.tournament.dao.TournamentScoreRecordDao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * @author moryzang
 */
@Repository
public class TournamentScoreRecordMybatisRepository implements TournamentScoreRecordRepository {
    private final TournamentScoreRecordDao tournamentScoreRecordDao;

    public TournamentScoreRecordMybatisRepository(TournamentScoreRecordDao tournamentScoreRecordDao) {
        this.tournamentScoreRecordDao = tournamentScoreRecordDao;
    }

    @Override
    public Optional<TournamentScoreRecord> findByEventGameIdAndParticipantIdAndCycleNumber(BigInteger eventGameId,
            BigInteger partyId, int currentStage) {
        return tournamentScoreRecordDao.findByEventGameIdAndParticipantIdAndCycleNumber(eventGameId, partyId,
                currentStage);
    }

    @Override
    public int create(TournamentScoreRecord tournamentScoreRecord) {
        return tournamentScoreRecordDao.insert(tournamentScoreRecord);
    }

    @Override
    public int bulkCreate(List<TournamentScoreRecord> tournamentScoreRecords) {
        return tournamentScoreRecordDao.insertAll(tournamentScoreRecords);
    }

    @Override
    public int update(TournamentScoreRecord tournamentScoreRecord) {
        return tournamentScoreRecordDao.update(tournamentScoreRecord);
    }

    @Override
    public List<TournamentScoreRecord> findByEventGameIdAndCycleNumber(BigInteger eventGameId, Integer cycleNumber) {
        return tournamentScoreRecordDao.findByEventGameIdAndCycleNumber(eventGameId, cycleNumber);
    }

    @Override
    public List<TournamentScoreRecord> findByEventGameIdAndParticipantId(BigInteger eventGameId, BigInteger participantId) {
        return tournamentScoreRecordDao.findByEventGameIdAndParticipantId(eventGameId, participantId);
    }

    @Override
    public <T> List<T> findByEventGameIdAndScoreTypeAndIdGreaterThan(BigInteger eventGameId, TournamentScoreType scoreType, BigInteger id, Pageable pageable, Class<T> projectionType) {
        return tournamentScoreRecordDao.findByEventGameIdAndScoreTypeAndIdGreaterThan(eventGameId,scoreType,id,pageable,projectionType);
    }
}
