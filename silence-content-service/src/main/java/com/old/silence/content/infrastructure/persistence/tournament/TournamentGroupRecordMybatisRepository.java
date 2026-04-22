package com.old.silence.content.infrastructure.persistence.tournament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.model.TournamentGroupRecord;
import com.old.silence.content.domain.repository.tournament.TournamentGroupRecordRepository;
import com.old.silence.content.infrastructure.persistence.tournament.dao.TournamentGroupRecordDao;

import java.math.BigInteger;
import java.util.List;

@Repository
public class TournamentGroupRecordMybatisRepository implements TournamentGroupRecordRepository {

    private final TournamentGroupRecordDao tournamentGroupRecordDao;

    public TournamentGroupRecordMybatisRepository(TournamentGroupRecordDao tournamentGroupRecordDao) {
        this.tournamentGroupRecordDao = tournamentGroupRecordDao;
    }

    @Override
    public <T> List<T> findByGroupIdAndParticipantType(BigInteger groupId, TournamentParticipantType participantType, Class<T> projectionType) {
        return tournamentGroupRecordDao.findByGroupIdAndParticipantType(groupId, participantType, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return tournamentGroupRecordDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public <T> List<T> findByGroupId(BigInteger groupId, Class<T> projectionType) {
        return tournamentGroupRecordDao.findByGroupId(groupId, projectionType);
    }

    @Override
    public int create(TournamentGroupRecord record) {
        return tournamentGroupRecordDao.insert(record);
    }

    @Override
    public int saveAll(List<TournamentGroupRecord> newGroups) {
        return tournamentGroupRecordDao.insertAll(newGroups);
    }
}
