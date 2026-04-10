package com.old.silence.content.infrastructure.persistence.dao.tournament;

import com.old.silence.content.domain.model.tournament.TournamentRanking;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

public interface TournamentRankingDao extends JdbcRepository<TournamentRanking, BigInteger> {
}
