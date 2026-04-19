package com.old.silence.content.domain.repository.tournament;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.content.domain.model.TournamentTask;

import java.math.BigInteger;
import java.util.Optional;

public interface TournamentTaskRepository {

    boolean existsByTournamentId(BigInteger tournamentId);

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(TournamentTask tournamentTask);

    int update(TournamentTask tournamentTask);

    int updateStatusAsLock(BigInteger id, TournamentTaskStatus newStatus, TournamentTaskStatus oldStatus);

}
