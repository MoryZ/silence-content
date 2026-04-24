package com.old.silence.content.domain.repository.tournament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.model.TournamentGroupRecord;

import java.math.BigInteger;
import java.util.List;

public interface TournamentGroupRecordRepository {


    int create(TournamentGroupRecord tournamentGroupRecord);

    int saveAll(List<TournamentGroupRecord> newGroups);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    <T> List<T> findByGroupIdAndIdGraterThan(BigInteger groupId, BigInteger id, Pageable pageable, Class<T> tournamentGroupRecordClass);
}
