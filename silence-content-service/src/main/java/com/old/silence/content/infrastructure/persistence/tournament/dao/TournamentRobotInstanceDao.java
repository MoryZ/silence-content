package com.old.silence.content.infrastructure.persistence.tournament.dao;

import org.apache.ibatis.annotations.Mapper;
import com.old.silence.content.domain.enums.tournament.TournamentRobotStatus;
import com.old.silence.content.domain.model.TournamentRobotInstance;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.Optional;

/**
 * @author moryzang
 */
@Mapper
public interface TournamentRobotInstanceDao extends JdbcRepository<TournamentRobotInstance, BigInteger> {

    Optional<TournamentRobotInstance> findByEventGameIdAndRobotIdAndStatus(BigInteger eventGameId, BigInteger robotId,
            TournamentRobotStatus status);
}
