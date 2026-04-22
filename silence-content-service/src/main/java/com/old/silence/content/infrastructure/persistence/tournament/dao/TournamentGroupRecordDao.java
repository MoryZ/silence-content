package com.old.silence.content.infrastructure.persistence.tournament.dao;

import org.apache.ibatis.annotations.Mapper;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.data.jdbc.repository.JdbcCrudRepository;
import com.old.silence.content.domain.model.TournamentGroupRecord;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface TournamentGroupRecordDao extends JdbcRepository<TournamentGroupRecord, BigInteger> {

    <T> List<T> findByGroupIdAndParticipantType(BigInteger groupId, TournamentParticipantType participantType, Class<T> projectionType);

    <T> List<T> findByGroupId(BigInteger groupId, Class<T> projectionType);
}
