package com.old.silence.content.domain.repository.tournament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.tournament.TournamentParticipationRecord;

import java.math.BigInteger;
import java.util.Optional;

/**
 * TournamentParticipationRecord仓储接口
 */
public interface TournamentParticipationRecordRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(TournamentParticipationRecord record);

    int update(TournamentParticipationRecord record);

    int deleteById(BigInteger id);
}
