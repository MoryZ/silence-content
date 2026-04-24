package com.old.silence.content.infrastructure.persistence.tournament.dao;

import com.old.silence.content.api.tournament.tournament.dto.TournamentParticipantGroupDto;
import com.old.silence.content.domain.enums.tournament.TournamentStageType;
import com.old.silence.content.domain.model.TournamentGroup;
import com.old.silence.data.mybatis.repository.MyBatisRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;



public interface TournamentGroupDao extends MyBatisRepository<TournamentGroup, BigInteger> {

    TournamentParticipantGroupDto findParticipantGroup(BigInteger eventGameId, BigInteger participantId, Integer participantType, Integer stageType, Integer stageNumber);

    Optional<TournamentGroup> findByIdAndEventGameId(BigInteger id, BigInteger eventGameId);

    List<TournamentGroup> findByEventGameIdAndStageTypeAndStageNumber(BigInteger eventGameId, TournamentStageType stageType, Integer cycleNumber);
}
