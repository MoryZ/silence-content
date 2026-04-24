package com.old.silence.content.infrastructure.persistence.tournament.dao;


import org.apache.ibatis.annotations.Mapper;
import com.old.silence.content.domain.model.TournamentConfig;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface TournamentConfigDao extends JdbcRepository<TournamentConfig, BigInteger> {

    <T> Optional<T> findByEventGameId(BigInteger eventGameId, Class<T> projectionType);
}
