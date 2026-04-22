package com.old.silence.content.infrastructure.persistence.tournament;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.model.TournamentParticipationRecord;
import com.old.silence.content.domain.repository.tournament.TournamentParticipationRecordRepository;
import com.old.silence.content.infrastructure.persistence.tournament.dao.TournamentParticipationRecordDao;

import java.math.BigInteger;
import java.util.List;

@Repository
public class TournamentParticipationRecordMyBatisRepository implements TournamentParticipationRecordRepository {

    private final TournamentParticipationRecordDao tournamentParticipationRecordDao;

    public TournamentParticipationRecordMyBatisRepository(TournamentParticipationRecordDao tournamentParticipationRecordDao) {
        this.tournamentParticipationRecordDao = tournamentParticipationRecordDao;
    }

    @Override
    public int create(TournamentParticipationRecord tournamentParticipationRecord) {
        return tournamentParticipationRecordDao.insert(tournamentParticipationRecord);
    }

    @Override
    public TournamentParticipationRecord findByEventGameIdAndParticipantIdAndParticipantType(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType participantType) {
        return tournamentParticipationRecordDao.findByEventGameIdAndParticipantIdAndParticipantType(eventGameId, participantId, participantType);
    }

    @Override
    public int countByEventGameId(BigInteger eventGameId) {
        return tournamentParticipationRecordDao.countByEventGameId(eventGameId);
    }

    @Override
    public List<TournamentParticipationRecord> findByEventGameIdAndStatus(BigInteger eventGameId,
            TournamentParticipantStatus tournamentParticipantStatus) {
        return tournamentParticipationRecordDao.findByEventGameIdAndStatus(eventGameId, tournamentParticipantStatus);
    }

    @Override
    public List<TournamentParticipationRecord> findByEventGameIdAndParticipantTypeAndStatus(BigInteger eventGameId, TournamentParticipantType participantType, TournamentParticipantStatus tournamentParticipantStatus) {
        return tournamentParticipationRecordDao.findByEventGameIdAndParticipantTypeAndStatus(eventGameId, participantType, tournamentParticipantStatus);
    }

    @Override
    public <T> List<T> findByEventGameIdAndParticipantTypeAndIdGreaterThan(BigInteger eventGameId, TournamentParticipantType participantType, BigInteger id, Pageable pageable, Class<T> projectionType) {
       return tournamentParticipationRecordDao.findByEventGameIdAndParticipantTypeAndIdGreaterThan(eventGameId, participantType, id, pageable, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return tournamentParticipationRecordDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int bulkCreate(List<TournamentParticipationRecord> tournamentParticipationRecords) {
        return tournamentParticipationRecordDao.insertAll(tournamentParticipationRecords);
    }
}
