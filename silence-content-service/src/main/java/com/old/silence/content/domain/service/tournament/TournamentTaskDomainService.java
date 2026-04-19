package com.old.silence.content.domain.service.tournament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.old.silence.content.api.tournament.tournament.dto.TournamentTaskQuery;
import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.content.domain.model.TournamentTask;
import com.old.silence.content.domain.repository.tournament.TournamentTaskRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class TournamentTaskDomainService {

    private final TournamentTaskRepository tournamentTaskRepository;

    public TournamentTaskDomainService(TournamentTaskRepository tournamentTaskRepository) {
        this.tournamentTaskRepository = tournamentTaskRepository;
    }

    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return tournamentTaskRepository.findById(id, projectionType);
    }

    public <T> Page<T> query(TournamentTaskQuery tournamentTaskQuery, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(tournamentTaskQuery, TournamentTask.class);
        return tournamentTaskRepository.findByCriteria(criteria, pageable, projectionType);
    }

    public void updateStatusAsLock(BigInteger id, TournamentTaskStatus newStatus, TournamentTaskStatus oldStatus) {
        // 乐观锁
        tournamentTaskRepository.updateStatusAsLock(id, newStatus, oldStatus);

    }

    public void update(TournamentTask tournamentTask) {
        tournamentTaskRepository.update(tournamentTask);
    }
}
