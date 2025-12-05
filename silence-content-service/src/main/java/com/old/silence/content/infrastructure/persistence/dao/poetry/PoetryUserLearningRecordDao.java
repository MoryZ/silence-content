package com.old.silence.content.infrastructure.persistence.dao.poetry;

import com.old.silence.content.domain.model.poetry.PoetryUserLearningRecord;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
* PoetryUserLearningRecord数据访问接口
*/
public interface PoetryUserLearningRecordDao extends JdbcRepository<PoetryUserLearningRecord, BigInteger> {

}