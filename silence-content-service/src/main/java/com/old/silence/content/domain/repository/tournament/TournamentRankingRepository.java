package com.old.silence.content.domain.repository.tournament;


import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentRankingType;
import com.old.silence.content.domain.model.TournamentRanking;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * @author moryzang
 */
public interface TournamentRankingRepository {

    Optional<TournamentRanking> findByEventGameIdAndParticipantIdAndRankingType(BigInteger eventGameId,
                                                                                BigInteger participantId, TournamentRankingType rankType);
    List<TournamentRanking> findByEventGameIdAndRankingTypeAndGroupId(BigInteger eventGameId, TournamentRankingType rankType, BigInteger groupId);

    <T> Optional<T> findByEventGameIdAndParticipantIdAndParticipantTypeAndRankingType(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType participantType, TournamentRankingType tournamentRankingType, Class<T> projectionType);

    int bulkCreate(List<TournamentRanking> tournamentRankings);

}
