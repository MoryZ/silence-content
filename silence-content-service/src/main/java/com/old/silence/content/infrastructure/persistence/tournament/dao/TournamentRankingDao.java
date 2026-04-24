package com.old.silence.content.infrastructure.persistence.tournament.dao;

import org.apache.ibatis.annotations.Mapper;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentRankingType;
import com.old.silence.data.jdbc.repository.JdbcCrudRepository;
import com.old.silence.content.domain.model.TournamentRanking;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * @author moryzang
 */
public interface TournamentRankingDao extends JdbcCrudRepository<TournamentRanking, BigInteger> {
    List<TournamentRanking> findByEventGameIdAndRankingTypeAndGroupId(BigInteger eventGameId,
                                                                      TournamentRankingType rankType, BigInteger bigInteger);

    Optional<TournamentRanking> findByEventGameIdAndParticipantIdAndRankingType(BigInteger eventGameId,
            BigInteger participantId, TournamentRankingType rankType);

    <T> Optional<T> findByEventGameIdAndParticipantIdAndParticipantTypeAndRankingType(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType participantType, TournamentRankingType rankingType);
}
