package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.tournament.TournamentGroup;
import com.old.silence.content.domain.repository.tournament.TournamentGroupRepository;
import com.old.silence.content.infrastructure.persistence.dao.tournament.TournamentGroupDao;

import java.math.BigInteger;
import java.util.Optional;

/**
 * TournamentGroup仓储实现
 */
@Repository
public class TournamentGroupMyBatisRepository implements TournamentGroupRepository {

    private final TournamentGroupDao tournamentGroupDao;

    public TournamentGroupMyBatisRepository(TournamentGroupDao tournamentGroupDao) {
        this.tournamentGroupDao = tournamentGroupDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return tournamentGroupDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return tournamentGroupDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(TournamentGroup tournamentGroup) {
        return tournamentGroupDao.insert(tournamentGroup);
    }

    @Override
    public int update(TournamentGroup tournamentGroup) {
        return tournamentGroupDao.update(tournamentGroup);
    }

    @Override
    public int deleteById(BigInteger id) {
        return tournamentGroupDao.deleteById(id);
    }
}
