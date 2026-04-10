package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.tournament.TournamentParticipationRecord;
import com.old.silence.content.domain.repository.tournament.TournamentParticipationRecordRepository;
import com.old.silence.content.infrastructure.persistence.dao.tournament.TournamentParticipationRecordDao;

import java.math.BigInteger;
import java.util.Optional;

/**
 * TournamentParticipationRecord仓储实现
 */
@Repository
public class TournamentParticipationRecordMyBatisRepository implements TournamentParticipationRecordRepository {

    private final TournamentParticipationRecordDao dao;

    public TournamentParticipationRecordMyBatisRepository(TournamentParticipationRecordDao dao) {
        this.dao = dao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return dao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return dao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(TournamentParticipationRecord record) {
        return dao.insert(record);
    }

    @Override
    public int update(TournamentParticipationRecord record) {
        return dao.update(record);
    }

    @Override
    public int deleteById(BigInteger id) {
        return dao.deleteById(id);
    }
}
