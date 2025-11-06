package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.PoetryAnswerRecords;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.List;

/**
* PoetryAnswerRecords数据访问接口
*/
public interface PoetryAnswerRecordsDao extends JdbcRepository<PoetryAnswerRecords, BigInteger> {

    <T> List<T> findByContentIdAndSubCategoryIdAndUserId(BigInteger contentId, BigInteger subCategoryId, BigInteger userId, Class<T> projectionType);
}