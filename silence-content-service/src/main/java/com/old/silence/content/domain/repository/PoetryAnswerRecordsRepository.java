package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import com.old.silence.content.domain.model.PoetryAnswerRecords;

import java.math.BigInteger;
import java.util.Optional;

/**
* PoetryAnswerRecords仓储接口
*/
public interface PoetryAnswerRecordsRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(PoetryAnswerRecords poetryAnswerRecords);

    int update(PoetryAnswerRecords poetryAnswerRecords);

    int deleteById(BigInteger id);
}