package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.infrastructure.persistence.dao.support.BigDecimalStatsVo;
import com.old.silence.content.infrastructure.persistence.dao.support.NumberStatsVo;
import com.old.silence.content.domain.model.PoetryAnswerRecords;
import com.old.silence.content.domain.repository.PoetryAnswerRecordsRepository;
import com.old.silence.content.infrastructure.persistence.dao.PoetryAnswerRecordsDao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
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
    public <T> List<T> findByContentIdAndSubCategoryIdAndUserId(BigInteger contentId, BigInteger subCategoryId, BigInteger userId, Class<T> projectionType) {
        return poetryAnswerRecordsDao.findByContentIdAndSubCategoryIdAndUserId(contentId, subCategoryId, userId, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return poetryAnswerRecordsDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public List<BigDecimalStatsVo> findMaxAccuracyTop5() {
        return List.of(
                new BigDecimalStatsVo(new BigInteger("1"), new BigDecimal("66.7")),
                new BigDecimalStatsVo(new BigInteger("2"), new BigDecimal("56.7")),
                new BigDecimalStatsVo(new BigInteger("3"), new BigDecimal("46.7")),
                new BigDecimalStatsVo(new BigInteger("4"), new BigDecimal("36.7")),
                new BigDecimalStatsVo(new BigInteger("5"), new BigDecimal("26.7"))
        );
    }

    @Override
    public List<NumberStatsVo> findMaxAnswerTop5() {
        return List.of(

        );
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