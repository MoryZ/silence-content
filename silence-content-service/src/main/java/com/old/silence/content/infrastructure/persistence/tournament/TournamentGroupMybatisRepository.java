package com.old.silence.content.infrastructure.persistence.tournament;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.api.tournament.tournament.dto.TournamentParticipantGroupDto;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentStageType;
import com.old.silence.content.domain.model.TournamentGroup;
import com.old.silence.content.domain.repository.tournament.TournamentGroupRepository;
import com.old.silence.content.infrastructure.persistence.tournament.dao.TournamentGroupDao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class TournamentGroupMybatisRepository implements TournamentGroupRepository {

    private final TournamentGroupDao tournamentGroupDao;

    public TournamentGroupMybatisRepository(TournamentGroupDao tournamentGroupDao) {
        this.tournamentGroupDao = tournamentGroupDao;
    }

    @Override
    public TournamentParticipantGroupDto findParticipantGroup(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType participantType, TournamentStageType stageType, Integer stageNumber) {
        return tournamentGroupDao.findParticipantGroup(eventGameId, participantId, participantType.getValue().intValue(), stageType.getValue().intValue(), stageNumber);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return tournamentGroupDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(TournamentGroup tournamentGroup) {
        return tournamentGroupDao.insert(tournamentGroup);
    }

    @Override
    public Optional<TournamentGroup> findByIdAndEventGameId(BigInteger id, BigInteger eventGameId) {
        return tournamentGroupDao.findByIdAndEventGameId(id, eventGameId);
    }

    @Override
    public List<TournamentGroup> findByEventGameIdAndStageTypeAndStageNumber(BigInteger eventGameId, TournamentStageType stageType, Integer cycleNumber) {
        return tournamentGroupDao.findByEventGameIdAndStageTypeAndStageNumber(eventGameId, stageType, cycleNumber);
    }

    @Override
    public int saveAll(List<TournamentGroup> newGroups) {
        return tournamentGroupDao.insertAll(newGroups);
    }
}
