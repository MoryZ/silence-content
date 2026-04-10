package com.old.silence.content.infrastructure.persistence.dao.tournament;

import com.old.silence.content.domain.model.tournament.TournamentGroup;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * TournamentGroup数据访问接口
 */
public interface TournamentGroupDao extends JdbcRepository<TournamentGroup, BigInteger> {
}
