package com.old.silence.content.domain.repository.tournament;

import org.springframework.data.domain.Pageable;
import com.old.silence.content.domain.enums.tournament.TournamentScoreType;
import com.old.silence.content.domain.model.TournamentScoreRecord;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * TournamentScoreRecord仓储接口
 */
public interface TournamentScoreRecordRepository {

    Optional<TournamentScoreRecord> findByEventGameIdAndParticipantIdAndCycleNumber(BigInteger eventGameId,
            BigInteger partyId, int currentStage);

    List<TournamentScoreRecord> findByEventGameIdAndCycleNumber(BigInteger eventGameId, Integer cycleNumber);

    List<TournamentScoreRecord> findByEventGameIdAndParticipantId(BigInteger eventGameId, BigInteger participantId);

    int create(TournamentScoreRecord tournamentScoreRecord);

    int bulkCreate(List<TournamentScoreRecord> tournamentScoreRecords);

    int update(TournamentScoreRecord tournamentScoreRecord);

    <T> List<T> findByEventGameIdAndScoreTypeAndIdGreaterThan(BigInteger eventGameId, TournamentScoreType scoreType, BigInteger id, Pageable pageable, Class<T> projectionType);
}
