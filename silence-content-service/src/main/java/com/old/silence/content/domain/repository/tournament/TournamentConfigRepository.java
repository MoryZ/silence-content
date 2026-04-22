package com.old.silence.content.domain.repository.tournament;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.TournamentConfig;

import java.math.BigInteger;
import java.util.Optional;

public interface TournamentConfigRepository {

    <T> Optional<T> findById(BigInteger tournamentId, Class<T> projectionType);

    <T> Optional<T> findByEventGameId(BigInteger eventGameId, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(TournamentConfig tournamentConfig);

    int updateNonNull(TournamentConfig tournamentConfig);

}
