package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.PoetryAnswerRecords;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
* PoetryAnswerRecords数据访问接口
*/
public interface PoetryAnswerRecordsDao extends JdbcRepository<PoetryAnswerRecords, BigInteger> {

}