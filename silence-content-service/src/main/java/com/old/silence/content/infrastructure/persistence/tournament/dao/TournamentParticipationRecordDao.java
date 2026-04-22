package com.old.silence.content.infrastructure.persistence.tournament.dao;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.model.TournamentParticipationRecord;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface TournamentParticipationRecordDao extends JdbcRepository<TournamentParticipationRecord, BigInteger> {

    TournamentParticipationRecord findByEventGameIdAndParticipantIdAndParticipantType(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType participantType);

    int countByEventGameId(BigInteger eventGameId);

    List<TournamentParticipationRecord> findByEventGameIdAndStatus(BigInteger eventGameId, TournamentParticipantStatus tournamentParticipantStatus);

    List<TournamentParticipationRecord> findByEventGameIdAndParticipantTypeAndStatus(BigInteger eventGameId, TournamentParticipantType participantType, TournamentParticipantStatus tournamentParticipantStatus);

    <T> List<T> findByEventGameIdAndParticipantTypeAndIdGreaterThan(BigInteger eventGameId, TournamentParticipantType participantType, BigInteger id, Pageable pageable, Class<T> projectionType);
}



