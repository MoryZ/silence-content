package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.poetry.PoetryUserLearningStats;
import com.old.silence.content.domain.repository.poetry.PoetryUserLearningStatsRepository;
import com.old.silence.content.infrastructure.persistence.dao.poetry.PoetryUserLearningStatsDao;

/**
 * PoetryUserLearningStats仓储实现
 */
@Repository
public class PoetryUserLearningStatsMyBatisRepository implements PoetryUserLearningStatsRepository {
    private final PoetryUserLearningStatsDao poetryUserLearningStatsDao;

    public PoetryUserLearningStatsMyBatisRepository(PoetryUserLearningStatsDao poetryUserLearningStatsDao) {
        this.poetryUserLearningStatsDao = poetryUserLearningStatsDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryUserLearningStatsDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return poetryUserLearningStatsDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(PoetryUserLearningStats poetryUserLearningStats) {
        return poetryUserLearningStatsDao.insert(poetryUserLearningStats);
    }

    @Override
    public int update(PoetryUserLearningStats poetryUserLearningStats) {
        return poetryUserLearningStatsDao.update(poetryUserLearningStats);
    }

    @Override
    public int deleteById(BigInteger id) {
        return poetryUserLearningStatsDao.deleteById(id);
    }
}