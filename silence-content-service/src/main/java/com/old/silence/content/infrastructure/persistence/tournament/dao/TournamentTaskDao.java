package com.old.silence.content.infrastructure.persistence.tournament.dao;


import org.apache.ibatis.annotations.Param;
import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.content.domain.model.TournamentTask;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

public interface TournamentTaskDao extends JdbcRepository<TournamentTask, BigInteger> {

    boolean existsByTournamentId(BigInteger tournamentId);

    int updateStatusAsLock(@Param("id") BigInteger id, @Param("newStatus") TournamentTaskStatus newStatus, @Param("oldStatus") TournamentTaskStatus oldStatus);
}
