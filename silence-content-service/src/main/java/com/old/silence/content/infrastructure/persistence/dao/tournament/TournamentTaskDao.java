package com.old.silence.content.infrastructure.persistence.dao.tournament;

import com.old.silence.content.domain.model.TournamentTask;
import com.old.silence.data.jdbc.repository.JdbcCrudRepository;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * TournamentTask数据访问接口
 */
public interface TournamentTaskDao extends JdbcRepository<TournamentTask, BigInteger> {
}
