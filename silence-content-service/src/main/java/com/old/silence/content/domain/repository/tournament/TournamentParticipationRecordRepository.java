package com.old.silence.content.domain.repository.tournament;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.model.TournamentParticipationRecord;

import java.math.BigInteger;
import java.util.List;

public interface TournamentParticipationRecordRepository {

    TournamentParticipationRecord findByEventGameIdAndParticipantIdAndParticipantType(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType participantType);

    int countByEventGameId(BigInteger eventGameId);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    List<TournamentParticipationRecord> findByEventGameIdAndStatus(BigInteger eventGameId, TournamentParticipantStatus tournamentParticipantStatus);

    List<TournamentParticipationRecord> findByEventGameIdAndParticipantTypeAndStatus(BigInteger eventGameId, TournamentParticipantType participantType, TournamentParticipantStatus tournamentParticipantStatus);

    int create(TournamentParticipationRecord tournamentParticipationRecord);

    int bulkCreate(List<TournamentParticipationRecord> tournamentParticipationRecords);

    <T> List<T> findByEventGameIdAndParticipantTypeAndIdGreaterThan(BigInteger eventGameId, TournamentParticipantType participantType, BigInteger id, Pageable pageable, Class<T> projectionType);
}
