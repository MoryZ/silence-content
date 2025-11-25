package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.model.LiveBroadcaster;
import com.old.silence.content.domain.repository.LiveBroadcasterRepository;
import com.old.silence.content.infrastructure.persistence.dao.LiveBroadcasterDao;

import java.math.BigInteger;
import java.util.Optional;

/**
* LiveBroadcaster仓储实现
*/
@Repository
public class LiveBroadcasterMyBatisRepository implements LiveBroadcasterRepository {
    private final LiveBroadcasterDao liveBroadcasterDao;

    public LiveBroadcasterMyBatisRepository(LiveBroadcasterDao liveBroadcasterDao) {
        this.liveBroadcasterDao = liveBroadcasterDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return liveBroadcasterDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return liveBroadcasterDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(LiveBroadcaster liveBroadcaster) {
        return liveBroadcasterDao.insert(liveBroadcaster);
    }

    @Override
    public int update(LiveBroadcaster liveBroadcaster) {
        return liveBroadcasterDao.update(liveBroadcaster);
    }

    @Override
    public int deleteById(BigInteger id) {
        return liveBroadcasterDao.deleteById(id);
    }
}