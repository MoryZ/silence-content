package com.old.silence.content.infrastructure.persistence.dao.tournament;

import com.old.silence.content.domain.model.tournament.TournamentParticipationRecord;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * TournamentParticipationRecord数据访问接口
 */
public interface TournamentParticipationRecordDao extends JdbcRepository<TournamentParticipationRecord, BigInteger> {
}
