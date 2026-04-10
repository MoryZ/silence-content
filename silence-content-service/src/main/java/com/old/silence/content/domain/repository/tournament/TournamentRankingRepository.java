package com.old.silence.content.domain.repository.tournament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.tournament.TournamentRanking;

import java.math.BigInteger;
import java.util.Optional;

public interface TournamentRankingRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(TournamentRanking record);

    int update(TournamentRanking record);

    int deleteById(BigInteger id);
}
