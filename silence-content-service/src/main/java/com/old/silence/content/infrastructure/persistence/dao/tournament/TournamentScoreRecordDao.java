package com.old.silence.content.infrastructure.persistence.dao.tournament;

import com.old.silence.content.domain.model.TournamentScoreRecord;
import com.old.silence.data.jdbc.repository.JdbcCrudRepository;

import java.math.BigInteger;

/**
 * TournamentScoreRecord数据访问接口
 */
public interface TournamentScoreRecordDao extends JdbcCrudRepository<TournamentScoreRecord, BigInteger> {
}
