package com.old.silence.content.infrastructure.persistence.tournament;

import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.tournament.TournamentRobotStatus;
import com.old.silence.content.domain.model.TournamentRobotInstance;
import com.old.silence.content.domain.repository.tournament.TournamentRobotInstanceRepository;
import com.old.silence.content.infrastructure.persistence.tournament.dao.TournamentRobotInstanceDao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * @author moryzang
 */
@Repository
public class TournamentRobotInstanceMybatisRepository implements TournamentRobotInstanceRepository {
    private final TournamentRobotInstanceDao tournamentRobotInstanceDao;

    public TournamentRobotInstanceMybatisRepository(TournamentRobotInstanceDao tournamentRobotInstanceDao) {
        this.tournamentRobotInstanceDao = tournamentRobotInstanceDao;
    }

    @Override
    public Optional<TournamentRobotInstance> findByEventGameIdAndRobotIdAndStatus(BigInteger eventGameId,
            BigInteger robotId,  TournamentRobotStatus status) {
        return tournamentRobotInstanceDao.findByEventGameIdAndRobotIdAndStatus(eventGameId, robotId,
                status);
    }

    @Override
    public List<TournamentRobotInstance> findByIds(List<BigInteger> ids) {
        return tournamentRobotInstanceDao.findAllById(ids);
    }

    @Override
    public int bulkCreate(List<TournamentRobotInstance> tournamentRobotInstances) {
        return tournamentRobotInstanceDao.insertAll(tournamentRobotInstances);
    }
}
