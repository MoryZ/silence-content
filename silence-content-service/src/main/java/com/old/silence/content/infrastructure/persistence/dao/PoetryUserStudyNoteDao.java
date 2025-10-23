package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.PoetryUserStudyNote;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
* PoetryUserStudyNote数据访问接口
*/
public interface PoetryUserStudyNoteDao extends JdbcRepository<PoetryUserStudyNote, BigInteger> {

}