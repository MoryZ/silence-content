package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.tournament.TournamentConfig;
import com.old.silence.content.domain.repository.tournament.TournamentConfigRepository;
import com.old.silence.content.infrastructure.persistence.dao.tournament.TournamentConfigDao;

import java.math.BigInteger;
import java.util.Optional;

/**
 * TournamentConfig仓储实现
 */
@Repository
public class TournamentConfigMyBatisRepository implements TournamentConfigRepository {
    private final TournamentConfigDao tournamentConfigDao;

    public TournamentConfigMyBatisRepository(TournamentConfigDao tournamentConfigDao) {
        this.tournamentConfigDao = tournamentConfigDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return tournamentConfigDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return tournamentConfigDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(TournamentConfig tournamentConfig) {
        return tournamentConfigDao.insert(tournamentConfig);
    }

    @Override
    public int update(TournamentConfig tournamentConfig) {
        return tournamentConfigDao.update(tournamentConfig);
    }

    @Override
    public int deleteById(BigInteger id) {
        return tournamentConfigDao.deleteById(id);
    }
}
