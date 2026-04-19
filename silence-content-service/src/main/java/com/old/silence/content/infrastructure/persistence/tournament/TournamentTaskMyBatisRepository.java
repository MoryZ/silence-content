package com.old.silence.content.infrastructure.persistence.tournament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.content.domain.model.TournamentTask;
import com.old.silence.content.domain.repository.tournament.TournamentTaskRepository;
import com.old.silence.content.infrastructure.persistence.tournament.dao.TournamentTaskDao;

import java.math.BigInteger;
import java.util.Optional;

/**
 * @author EX-ZHANGMENGWEI001
 */
@Repository
public class TournamentTaskMyBatisRepository implements TournamentTaskRepository {

    private final TournamentTaskDao tournamentTaskDao;

    public TournamentTaskMyBatisRepository(TournamentTaskDao tournamentTaskDao) {
        this.tournamentTaskDao = tournamentTaskDao;
    }

    @Override
    public boolean existsByTournamentId(BigInteger tournamentId) {
        return tournamentTaskDao.existsByTournamentId(tournamentId);
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
    public int create(TournamentTask tournamentTask) {
        return tournamentTaskDao.insert(tournamentTask);
    }

    @Override
    public int update(TournamentTask tournamentTask) {
        return tournamentTaskDao.updateNonNull(tournamentTask);
    }

    @Override
    public int updateStatusAsLock(BigInteger id, TournamentTaskStatus newStatus, TournamentTaskStatus oldStatus) {
        return tournamentTaskDao.updateStatusAsLock(id, newStatus, oldStatus);
    }
}
