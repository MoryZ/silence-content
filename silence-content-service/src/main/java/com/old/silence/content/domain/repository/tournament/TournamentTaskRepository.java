package com.old.silence.content.domain.repository.tournament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.tournament.TournamentTask;

import java.math.BigInteger;
import java.util.Optional;

/**
 * TournamentTask仓储
 */
public interface TournamentTaskRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(TournamentTask task);

    int update(TournamentTask task);

    int deleteById(BigInteger id);
}
