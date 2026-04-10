package com.old.silence.content.domain.repository.tournament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.tournament.TournamentGroupRecord;

import java.math.BigInteger;
import java.util.Optional;

/**
 * TournamentGroupRecord仓储接口
 */
public interface TournamentGroupRecordRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(TournamentGroupRecord record);

    int update(TournamentGroupRecord record);

    int deleteById(BigInteger id);
}
