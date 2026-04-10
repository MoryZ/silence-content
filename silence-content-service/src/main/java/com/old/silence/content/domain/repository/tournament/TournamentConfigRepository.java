package com.old.silence.content.domain.repository.tournament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.tournament.TournamentConfig;

import java.math.BigInteger;
import java.util.Optional;

/**
 * TournamentConfig仓储接口
 */
public interface TournamentConfigRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(TournamentConfig tournamentConfig);

    int update(TournamentConfig tournamentConfig);

    int deleteById(BigInteger id);
}
