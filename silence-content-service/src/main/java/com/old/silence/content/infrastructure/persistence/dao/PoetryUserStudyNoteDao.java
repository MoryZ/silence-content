package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.PoetryUserStudyNote;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.List;

/**
* PoetryUserStudyNote数据访问接口
*/
public interface PoetryUserStudyNoteDao extends JdbcRepository<PoetryUserStudyNote, BigInteger> {

    <T> List<T> findByContentIdAndUserId(BigInteger contentId, BigInteger userId, Class<T> projectionType);
}