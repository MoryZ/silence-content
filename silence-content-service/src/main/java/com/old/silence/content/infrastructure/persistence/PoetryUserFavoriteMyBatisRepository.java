package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.infrastructure.persistence.dao.support.NumberStatsVo;
import com.old.silence.content.domain.model.PoetryUserFavorite;
import com.old.silence.content.domain.repository.PoetryUserFavoriteRepository;
import com.old.silence.content.infrastructure.persistence.dao.PoetryUserFavoriteDao;

/**
 * PoetryUserFavorite仓储实现
 */
@Repository
public class PoetryUserFavoriteMyBatisRepository implements PoetryUserFavoriteRepository {
    private final PoetryUserFavoriteDao poetryUserFavoriteDao;

    public PoetryUserFavoriteMyBatisRepository(PoetryUserFavoriteDao poetryUserFavoriteDao) {
        this.poetryUserFavoriteDao = poetryUserFavoriteDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryUserFavoriteDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return poetryUserFavoriteDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public List<NumberStatsVo> findFavoriteTop5() {
        return poetryUserFavoriteDao.findUserFavoriteStats();
    }

    @Override
    public int create(PoetryUserFavorite poetryUserFavorite) {
        return poetryUserFavoriteDao.insert(poetryUserFavorite);
    }

    @Override
    public int update(PoetryUserFavorite poetryUserFavorite) {
        return poetryUserFavoriteDao.update(poetryUserFavorite);
    }

    @Override
    public int deleteById(BigInteger id) {
        return poetryUserFavoriteDao.deleteById(id);
    }
}