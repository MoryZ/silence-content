package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.poetry.PoetryLearningContent;
import com.old.silence.content.domain.repository.poetry.PoetryLearningContentRepository;
import com.old.silence.content.infrastructure.persistence.dao.poetry.PoetryLearningContentDao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * PoetryLearningContent仓储实现
 */
@Repository
public class PoetryLearningContentMyBatisRepository implements PoetryLearningContentRepository {
    private final PoetryLearningContentDao poetryLearningContentDao;

    public PoetryLearningContentMyBatisRepository(PoetryLearningContentDao poetryLearningContentDao) {
        this.poetryLearningContentDao = poetryLearningContentDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryLearningContentDao.findById(id, projectionType);
    }

    @Override
    public <T> List<T> findByIds(List<BigInteger> ids, Class<T> projectionType) {
        return poetryLearningContentDao.findAllById(ids, projectionType);
    }

    @Override
    public <T> List<T> findByGradeIdAndSubCategoryId(BigInteger gradeId, BigInteger subCategoryId, Class<T> projectionType) {
        return poetryLearningContentDao.findByGradeIdAndSubCategoryId(gradeId, subCategoryId, projectionType);
    }

    @Override
    public long countByCriteria(Criteria criteria) {
        return poetryLearningContentDao.countByCriteria(criteria);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return poetryLearningContentDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(PoetryLearningContent poetryLearningContent) {
        return poetryLearningContentDao.insert(poetryLearningContent);
    }

    @Override
    public int bulkCreate(List<PoetryLearningContent> poetryLearningContents) {
        return poetryLearningContentDao.insertAll(poetryLearningContents);
    }

    @Override
    public int update(PoetryLearningContent poetryLearningContent) {
        return poetryLearningContentDao.update(poetryLearningContent);
    }

    @Override
    public int deleteById(BigInteger id) {
        return poetryLearningContentDao.deleteById(id);
    }
}