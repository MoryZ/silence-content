package com.old.silence.content.infrastructure.persistence.dao.tournament;

import com.old.silence.content.domain.model.tournament.TournamentTask;
import com.old.silence.content.domain.repository.tournament.TournamentTaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

/**
 * TournamentTask仓储实现
 */
@Repository
public class TournamentTaskMyBatisRepository implements TournamentTaskRepository {

    private final TournamentTaskDao tournamentTaskDao;

    public TournamentTaskMyBatisRepository(TournamentTaskDao tournamentTaskDao) {
        this.tournamentTaskDao = tournamentTaskDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return tournamentTaskDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return tournamentTaskDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(TournamentTask task) {
        return tournamentTaskDao.insert(task);
    }

    @Override
    public int update(TournamentTask task) {
        return tournamentTaskDao.update(task);
    }

    @Override
    public int deleteById(BigInteger id) {
        return tournamentTaskDao.deleteById(id);
    }
}
