package com.old.silence.content.infrastructure.persistence.dao.tournament;

import com.old.silence.content.domain.model.tournament.TournamentRanking;
import com.old.silence.content.domain.repository.tournament.TournamentRankingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public class TournamentRankingMyBatisRepository implements TournamentRankingRepository {
    private final TournamentRankingDao tournamentRankingDao;

    public TournamentRankingMyBatisRepository(TournamentRankingDao tournamentRankingDao) {
        this.tournamentRankingDao = tournamentRankingDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return tournamentRankingDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return tournamentRankingDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(TournamentRanking record) {
        return tournamentRankingDao.insert(record);
    }

    @Override
    public int update(TournamentRanking record) {
        return tournamentRankingDao.update(record);
    }

    @Override
    public int deleteById(BigInteger id) {
        return tournamentRankingDao.deleteById(id);
    }
}
