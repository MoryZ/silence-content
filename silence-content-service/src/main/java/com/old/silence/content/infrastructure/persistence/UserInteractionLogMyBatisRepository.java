package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.api.vo.StatsVo;
import com.old.silence.content.domain.model.poetry.UserInteractionLog;
import com.old.silence.content.domain.repository.poetry.UserInteractionLogRepository;
import com.old.silence.content.infrastructure.persistence.dao.poetry.UserInteractionLogDao;
import com.old.silence.content.infrastructure.persistence.dao.support.PoetryAggregationDao;

/**
 * PoetryUserFavorite仓储实现
 */
@Repository
public class UserInteractionLogMyBatisRepository implements UserInteractionLogRepository {
    private final UserInteractionLogDao userInteractionLogDao;
    private final PoetryAggregationDao poetryAggregationDao;

    public UserInteractionLogMyBatisRepository(UserInteractionLogDao userInteractionLogDao,
                                               PoetryAggregationDao poetryAggregationDao) {
        this.userInteractionLogDao = userInteractionLogDao;
        this.poetryAggregationDao = poetryAggregationDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return userInteractionLogDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return userInteractionLogDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public List<StatsVo> findFavoriteTop5() {
        return poetryAggregationDao.findFavoriteTop5();
    }

    @Override
    public int create(UserInteractionLog userInteractionLog) {
        return userInteractionLogDao.insert(userInteractionLog);
    }

    @Override
    public int update(UserInteractionLog userInteractionLog) {
        return userInteractionLogDao.update(userInteractionLog);
    }

    @Override
    public int deleteById(BigInteger id) {
        return userInteractionLogDao.deleteById(id);
    }
}