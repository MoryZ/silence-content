package com.old.silence.content.infrastructure.persistence.dao.tournament;

import com.old.silence.content.domain.model.tournament.TournamentScoreRecord;
import com.old.silence.content.domain.repository.tournament.TournamentScoreRecordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

/**
 * TournamentScoreRecord仓储实现
 */
@Repository
public class TournamentScoreRecordMyBatisRepository implements TournamentScoreRecordRepository {

    private final TournamentScoreRecordDao tournamentScoreRecordDao;

    public TournamentScoreRecordMyBatisRepository(TournamentScoreRecordDao tournamentScoreRecordDao) {
        this.tournamentScoreRecordDao = tournamentScoreRecordDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return tournamentScoreRecordDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return tournamentScoreRecordDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(TournamentScoreRecord record) {
        return tournamentScoreRecordDao.insert(record);
    }

    @Override
    public int update(TournamentScoreRecord record) {
        return tournamentScoreRecordDao.update(record);
    }

    @Override
    public int deleteById(BigInteger id) {
        return tournamentScoreRecordDao.deleteById(id);
    }
}
