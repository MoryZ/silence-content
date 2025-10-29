package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.PoetryUserLoginLog;
import com.old.silence.content.domain.repository.PoetryUserLoginLogRepository;
import com.old.silence.content.infrastructure.persistence.dao.PoetryUserLoginLogDao;

/**
 * PoetryUser仓储实现
 */
@Repository
public class PoetryUserLoginLogMyBatisRepository implements PoetryUserLoginLogRepository {
    private final PoetryUserLoginLogDao poetryUserLoginLogDao;

    public PoetryUserLoginLogMyBatisRepository(PoetryUserLoginLogDao poetryUserLoginLogDao) {
        this.poetryUserLoginLogDao = poetryUserLoginLogDao;
    }


    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryUserLoginLogDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return poetryUserLoginLogDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(PoetryUserLoginLog poetryUserLoginLog) {
        return poetryUserLoginLogDao.insert(poetryUserLoginLog);
    }

    @Override
    public int update(PoetryUserLoginLog poetryUserLoginLog) {
        return poetryUserLoginLogDao.update(poetryUserLoginLog);
    }

    @Override
    public int deleteById(BigInteger id) {
        return poetryUserLoginLogDao.deleteById(id);
    }
}