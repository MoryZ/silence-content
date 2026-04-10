package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.tournament.TournamentGroupRecord;
import com.old.silence.content.domain.repository.tournament.TournamentGroupRecordRepository;
import com.old.silence.content.infrastructure.persistence.dao.tournament.TournamentGroupRecordDao;

import java.math.BigInteger;
import java.util.Optional;

/**
 * TournamentGroupRecord仓储实现
 */
@Repository
public class TournamentGroupRecordMyBatisRepository implements TournamentGroupRecordRepository {

    private final TournamentGroupRecordDao tournamentGroupRecordDao;

    public TournamentGroupRecordMyBatisRepository(TournamentGroupRecordDao tournamentGroupRecordDao) {
        this.tournamentGroupRecordDao = tournamentGroupRecordDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return tournamentGroupRecordDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return tournamentGroupRecordDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(TournamentGroupRecord record) {
        return tournamentGroupRecordDao.insert(record);
    }

    @Override
    public int update(TournamentGroupRecord record) {
        return tournamentGroupRecordDao.update(record);
    }

    @Override
    public int deleteById(BigInteger id) {
        return tournamentGroupRecordDao.deleteById(id);
    }
}
