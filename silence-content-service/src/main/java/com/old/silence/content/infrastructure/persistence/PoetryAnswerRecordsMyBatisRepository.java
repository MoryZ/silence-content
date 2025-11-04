package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.model.PoetryAnswerRecords;
import com.old.silence.content.domain.repository.PoetryAnswerRecordsRepository;
import com.old.silence.content.infrastructure.persistence.dao.PoetryAnswerRecordsDao;

import java.math.BigInteger;
import java.util.Optional;

/**
* PoetryAnswerRecords仓储实现
*/
@Repository
public class PoetryAnswerRecordsMyBatisRepository implements PoetryAnswerRecordsRepository {
    private final PoetryAnswerRecordsDao poetryAnswerRecordsDao;

    public PoetryAnswerRecordsMyBatisRepository(PoetryAnswerRecordsDao poetryAnswerRecordsDao) {
        this.poetryAnswerRecordsDao = poetryAnswerRecordsDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryAnswerRecordsDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return poetryAnswerRecordsDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(PoetryAnswerRecords poetryAnswerRecords) {
        return poetryAnswerRecordsDao.insert(poetryAnswerRecords);
    }

    @Override
    public int update(PoetryAnswerRecords poetryAnswerRecords) {
        return poetryAnswerRecordsDao.update(poetryAnswerRecords);
    }

    @Override
    public int deleteById(BigInteger id) {
        return poetryAnswerRecordsDao.deleteById(id);
    }
}