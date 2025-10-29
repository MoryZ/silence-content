package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.PoetryUser;
import com.old.silence.content.domain.repository.PoetryUserRepository;
import com.old.silence.content.infrastructure.persistence.dao.PoetryUserDao;

/**
 * PoetryUser仓储实现
 */
@Repository
public class PoetryUserMyBatisRepository implements PoetryUserRepository {
    private final PoetryUserDao poetryUserDao;

    public PoetryUserMyBatisRepository(PoetryUserDao poetryUserDao) {
        this.poetryUserDao = poetryUserDao;
    }


    @Override
    public <T> Optional<T> findByOpenid(String openid, Class<T> projectionType) {
        return poetryUserDao.findByOpenid(openid, projectionType);
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryUserDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return poetryUserDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(PoetryUser poetryUser) {
        return poetryUserDao.insert(poetryUser);
    }

    @Override
    public int update(PoetryUser poetryUser) {
        return poetryUserDao.update(poetryUser);
    }

    @Override
    public int deleteById(BigInteger id) {
        return poetryUserDao.deleteById(id);
    }
}