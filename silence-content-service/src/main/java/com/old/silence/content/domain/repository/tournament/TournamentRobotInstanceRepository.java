package com.old.silence.content.domain.repository.tournament;

import com.old.silence.content.domain.enums.tournament.TournamentRobotStatus;
import com.old.silence.content.domain.model.TournamentRobotInstance;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * @author moryzang
 */
public interface TournamentRobotInstanceRepository {
    Optional<TournamentRobotInstance> findByEventGameIdAndRobotIdAndStatus(BigInteger eventGameId, BigInteger robotId,  TournamentRobotStatus status);
    List<TournamentRobotInstance> findByIds(List<BigInteger> ids);

    int bulkCreate(List<TournamentRobotInstance> tournamentRobotInstances);
}
