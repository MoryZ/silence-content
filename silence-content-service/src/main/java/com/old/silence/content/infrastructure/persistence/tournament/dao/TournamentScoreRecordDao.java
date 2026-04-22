package com.old.silence.content.infrastructure.persistence.tournament.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import com.old.silence.content.domain.enums.tournament.TournamentScoreType;
import com.old.silence.data.jdbc.repository.JdbcCrudRepository;
import com.old.silence.data.jdbc.repository.JdbcPagingAndSortingRepository;
import com.old.silence.content.domain.model.TournamentScoreRecord;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * @author moryzang
 */
@Mapper
public interface TournamentScoreRecordDao extends JdbcCrudRepository<TournamentScoreRecord, BigInteger> {

    Optional<TournamentScoreRecord> findByEventGameIdAndParticipantIdAndCycleNumber(BigInteger eventGameId, BigInteger partyId, int currentStage);

    List<TournamentScoreRecord> findByEventGameIdAndCycleNumber(BigInteger eventGameId, Integer cycleNumber);

    List<TournamentScoreRecord> findByEventGameIdAndParticipantId(BigInteger eventGameId, BigInteger participantId);

    <T> List<T> findByEventGameIdAndScoreTypeAndIdGreaterThan(BigInteger eventGameId, TournamentScoreType scoreType, BigInteger id, Pageable pageable, Class<T> projectionType);
}
