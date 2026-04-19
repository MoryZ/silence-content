package com.old.silence.content.infrastructure.persistence.tournament;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.TournamentConfig;
import com.old.silence.content.domain.repository.tournament.TournamentConfigRepository;
import com.old.silence.content.infrastructure.persistence.tournament.dao.TournamentConfigDao;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public class TournamentConfigMybatisRepository implements TournamentConfigRepository {

    private final TournamentConfigDao tournamentConfigDao;

    public TournamentConfigMybatisRepository(TournamentConfigDao tournamentConfigDao) {
        this.tournamentConfigDao = tournamentConfigDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return tournamentConfigDao.findById(id, projectionType);
    }

    @Override
    public <T> Optional<T> findByEventGameId(BigInteger eventGameId, Class<T> projectionType) {
        return tournamentConfigDao.findByEventGameId(eventGameId, projectionType);
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
    public int updateNonNull(TournamentConfig tournamentConfig) {
        return tournamentConfigDao.updateNonNull(tournamentConfig);
    }
}
