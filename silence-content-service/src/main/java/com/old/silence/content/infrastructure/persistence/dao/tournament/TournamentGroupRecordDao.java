package com.old.silence.content.infrastructure.persistence.dao.tournament;

import com.old.silence.content.domain.model.TournamentGroupRecord;
import com.old.silence.data.jdbc.repository.JdbcCrudRepository;

import java.math.BigInteger;

/**
 * TournamentGroupRecord数据访问接口
 */
public interface TournamentGroupRecordDao extends JdbcCrudRepository<TournamentGroupRecord, BigInteger> {
}
