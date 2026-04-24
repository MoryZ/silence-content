package com.old.silence.content.infrastructure.persistence.tournament.dao;

import org.springframework.data.domain.Pageable;
import com.old.silence.content.domain.model.TournamentGroupRecord;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.List;

public interface TournamentGroupRecordDao extends JdbcRepository<TournamentGroupRecord, BigInteger> {

    <T> List<T> findByGroupIdAndIdGreaterThan(BigInteger groupId, BigInteger id, Pageable pageable, Class<T> projectionType);
}
