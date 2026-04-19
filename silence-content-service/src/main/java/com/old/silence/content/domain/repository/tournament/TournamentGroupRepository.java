package com.old.silence.content.domain.repository.tournament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import com.old.silence.content.api.tournament.tournament.dto.TournamentParticipantGroupDto;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentStageType;
import com.old.silence.content.domain.model.TournamentGroup;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface TournamentGroupRepository {
    TournamentParticipantGroupDto findParticipantGroup(BigInteger eventGameId, BigInteger participantId,
                                                       TournamentParticipantType participantType, TournamentStageType stageType, Integer stageNumber);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(TournamentGroup tournamentGroup);

    Optional<TournamentGroup> findByIdAndEventGameId(BigInteger id, BigInteger eventGameId);

    int saveAll(List<TournamentGroup> newGroups);

    List<TournamentGroup> findByEventGameIdAndStageTypeAndStageNumber(BigInteger eventGameId, TournamentStageType tournamentStageType, Integer cycleNumber);
}
