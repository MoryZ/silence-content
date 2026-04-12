package com.old.silence.content.infrastructure.persistence.dao.tournament;

import com.old.silence.content.domain.model.tournament.TournamentScoreRecord;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * TournamentScoreRecord数据访问接口
 */
public interface TournamentScoreRecordDao extends JdbcRepository<TournamentScoreRecord, BigInteger> {
}
