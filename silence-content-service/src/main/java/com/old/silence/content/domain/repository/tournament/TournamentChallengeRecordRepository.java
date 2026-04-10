package com.old.silence.content.domain.repository.tournament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.tournament.TournamentChallengeRecord;

import java.math.BigInteger;
import java.util.Optional;

public interface TournamentChallengeRecordRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(TournamentChallengeRecord record);

    int update(TournamentChallengeRecord record);

    int deleteById(BigInteger id);
}
