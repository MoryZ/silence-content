package com.old.silence.content.infrastructure.persistence.dao.tournament;

import com.old.silence.content.domain.model.TournamentRanking;
import com.old.silence.data.jdbc.repository.JdbcCrudRepository;

import java.math.BigInteger;

public interface TournamentRankingDao extends JdbcCrudRepository<TournamentRanking, BigInteger> {
}
