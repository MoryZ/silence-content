package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.poetry.PoetryUserLearningRecord;
import com.old.silence.content.domain.repository.poetry.PoetryUserLearningRecordRepository;
import com.old.silence.content.infrastructure.persistence.dao.poetry.PoetryUserLearningRecordDao;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * PoetryUserLearningRecord仓储实现
 */
@Repository
public class PoetryUserLearningRecordMyBatisRepository implements PoetryUserLearningRecordRepository {
    private final PoetryUserLearningRecordDao poetryUserLearningRecordDao;

    public PoetryUserLearningRecordMyBatisRepository(PoetryUserLearningRecordDao poetryUserLearningRecordDao) {
        this.poetryUserLearningRecordDao = poetryUserLearningRecordDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryUserLearningRecordDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return poetryUserLearningRecordDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public <T> Optional<T> findByUserIdAndContentId(BigInteger userId, BigInteger contentId, Class<T> projectionType) {
        return poetryUserLearningRecordDao.findByUserIdAndContentId(userId, contentId, projectionType);
    }

    @Override
    public <T> List<T> findByUserIdAndSubCategoryIdAndNextReviewAtLessThanEqual(BigInteger userId, BigInteger subCategoryId, Instant cutoff, Class<T> projectionType) {
        return poetryUserLearningRecordDao.findByUserIdAndSubCategoryIdAndNextReviewAtLessThanEqual(userId, subCategoryId, cutoff, projectionType);
    }

    @Override
    public int create(PoetryUserLearningRecord poetryUserLearningRecord) {
        return poetryUserLearningRecordDao.insert(poetryUserLearningRecord);
    }

    @Override
    public int update(PoetryUserLearningRecord poetryUserLearningRecord) {
        return poetryUserLearningRecordDao.update(poetryUserLearningRecord);
    }

    @Override
    public int deleteById(BigInteger id) {
        return poetryUserLearningRecordDao.deleteById(id);
    }
}