package com.old.silence.content.infrastructure.persistence.dao.tournament;

import com.old.silence.content.domain.model.tournament.TournamentChallengeRecord;
import com.old.silence.content.domain.repository.tournament.TournamentChallengeRecordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public class TournamentChallengeRecordMyBatisRepository implements TournamentChallengeRecordRepository {
    private final TournamentChallengeRecordDao tournamentChallengeRecordDao;

    public TournamentChallengeRecordMyBatisRepository(TournamentChallengeRecordDao tournamentChallengeRecordDao) {
        this.tournamentChallengeRecordDao = tournamentChallengeRecordDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return tournamentChallengeRecordDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return tournamentChallengeRecordDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(TournamentChallengeRecord record) {
        return tournamentChallengeRecordDao.insert(record);
    }

    @Override
    public int update(TournamentChallengeRecord record) {
        return tournamentChallengeRecordDao.update(record);
    }

    @Override
    public int deleteById(BigInteger id) {
        return tournamentChallengeRecordDao.deleteById(id);
    }
}
