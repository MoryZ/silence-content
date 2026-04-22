package com.old.silence.content.infrastructure.persistence.tournament;

import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentRankingType;
import com.old.silence.content.domain.model.TournamentRanking;
import com.old.silence.content.domain.repository.tournament.TournamentRankingRepository;
import com.old.silence.content.infrastructure.persistence.tournament.dao.TournamentRankingDao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * @author moryzang
 */
@Repository
public class TournamentRankingMybatisRepository implements TournamentRankingRepository {
    private final TournamentRankingDao tournamentRankingDao;

    public TournamentRankingMybatisRepository(TournamentRankingDao tournamentRankingDao) {
        this.tournamentRankingDao = tournamentRankingDao;
    }

    @Override
    public List<TournamentRanking> findByEventGameIdAndRankingTypeAndGroupId(BigInteger eventGameId,
            TournamentRankingType rankType, BigInteger bigInteger) {
        return tournamentRankingDao.findByEventGameIdAndRankingTypeAndGroupId(eventGameId, rankType, bigInteger);
    }

    @Override
    public <T> Optional<T> findByEventGameIdAndParticipantIdAndParticipantTypeAndRankingType(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType participantType, TournamentRankingType rankingType, Class<T> projectionType) {
        return tournamentRankingDao.findByEventGameIdAndParticipantIdAndParticipantTypeAndRankingType(
                eventGameId,participantId,participantType,rankingType);
    }

    @Override
    public int bulkCreate(List<TournamentRanking> tournamentRankings) {
        return tournamentRankingDao.insertAll(tournamentRankings);
    }

    @Override
    public Optional<TournamentRanking> findByEventGameIdAndParticipantIdAndRankingType(BigInteger eventGameId,
            BigInteger participantId, TournamentRankingType rankType) {
        return tournamentRankingDao.findByEventGameIdAndParticipantIdAndRankingType(eventGameId, participantId,
                rankType);
    }
}
