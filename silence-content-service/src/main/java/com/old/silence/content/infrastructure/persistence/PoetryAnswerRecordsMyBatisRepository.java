package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.api.vo.StatsVo;
import com.old.silence.content.domain.model.PoetryAnswerRecords;
import com.old.silence.content.domain.repository.PoetryAnswerRecordsRepository;
import com.old.silence.content.infrastructure.persistence.dao.PoetryAnswerRecordsDao;
import com.old.silence.content.infrastructure.persistence.dao.support.PoetryAggregationDao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
* PoetryAnswerRecords仓储实现
*/
@Repository
public class PoetryAnswerRecordsMyBatisRepository implements PoetryAnswerRecordsRepository {
    private final PoetryAnswerRecordsDao poetryAnswerRecordsDao;
    private final PoetryAggregationDao poetryAggregationDao;

    public PoetryAnswerRecordsMyBatisRepository(PoetryAnswerRecordsDao poetryAnswerRecordsDao,
                                                PoetryAggregationDao poetryAggregationDao) {
        this.poetryAnswerRecordsDao = poetryAnswerRecordsDao;
        this.poetryAggregationDao = poetryAggregationDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryAnswerRecordsDao.findById(id, projectionType);
    }

    @Override
    public <T> List<T> findByContentIdAndSubCategoryIdAndUserId(BigInteger contentId, BigInteger subCategoryId, BigInteger userId, Class<T> projectionType) {
        return poetryAnswerRecordsDao.findByContentIdAndSubCategoryIdAndUserId(contentId, subCategoryId, userId, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return poetryAnswerRecordsDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public List<StatsVo> findMaxAccuracyTop5() {
        return poetryAggregationDao.findMaxAnswerAccuracyTop5();
    }

    @Override
    public List<StatsVo> findMaxAnswerTop5() {
        return poetryAggregationDao.findMaxAnswerCountTop5();
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