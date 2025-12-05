package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.poetry.PoetryCategory;
import com.old.silence.content.domain.repository.poetry.PoetryCategoryRepository;
import com.old.silence.content.infrastructure.persistence.dao.poetry.PoetryCategoryDao;

/**
 * PoetryCategory仓储实现
 */
@Repository
public class PoetryCategoryMyBatisRepository implements PoetryCategoryRepository {
    private final PoetryCategoryDao poetryCategoryDao;

    public PoetryCategoryMyBatisRepository(PoetryCategoryDao poetryCategoryDao) {
        this.poetryCategoryDao = poetryCategoryDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryCategoryDao.findById(id, projectionType);
    }

    @Override
    public <T> List<T> findByIds(List<BigInteger> ids, Class<T> projectionType) {
        return poetryCategoryDao.findAllById(ids, projectionType);
    }

    @Override
    public <T> List<T> findByParentId(BigInteger parentId, Class<T> projectionType) {
        return poetryCategoryDao.findByParentId(parentId, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return poetryCategoryDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(PoetryCategory poetryCategory) {
        return poetryCategoryDao.insert(poetryCategory);
    }

    @Override
    public int update(PoetryCategory poetryCategory) {
        return poetryCategoryDao.update(poetryCategory);
    }

    @Override
    public int deleteById(BigInteger id) {
        return poetryCategoryDao.deleteById(id);
    }
}