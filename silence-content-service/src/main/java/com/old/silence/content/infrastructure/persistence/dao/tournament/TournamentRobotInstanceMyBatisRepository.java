package com.old.silence.content.infrastructure.persistence.dao.tournament;

import com.old.silence.content.domain.model.tournament.TournamentRobotInstance;
import com.old.silence.content.domain.repository.tournament.TournamentRobotInstanceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public class TournamentRobotInstanceMyBatisRepository implements TournamentRobotInstanceRepository {
    private final TournamentRobotInstanceDao tournamentRobotInstanceDao;

    public TournamentRobotInstanceMyBatisRepository(TournamentRobotInstanceDao tournamentRobotInstanceDao) {
        this.tournamentRobotInstanceDao = tournamentRobotInstanceDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return tournamentRobotInstanceDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return tournamentRobotInstanceDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(TournamentRobotInstance record) {
        return tournamentRobotInstanceDao.insert(record);
    }

    @Override
    public int update(TournamentRobotInstance record) {
        return tournamentRobotInstanceDao.update(record);
    }

    @Override
    public int deleteById(BigInteger id) {
        return tournamentRobotInstanceDao.deleteById(id);
    }
}
