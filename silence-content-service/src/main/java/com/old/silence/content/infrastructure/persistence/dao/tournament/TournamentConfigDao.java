package com.old.silence.content.infrastructure.persistence.dao.tournament;

import com.old.silence.content.domain.model.tournament.TournamentConfig;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * TournamentConfig数据访问接口
 */
public interface TournamentConfigDao extends JdbcRepository<TournamentConfig, BigInteger> {

}
