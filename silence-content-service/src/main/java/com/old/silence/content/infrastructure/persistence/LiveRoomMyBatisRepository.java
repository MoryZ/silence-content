package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.model.LiveRoom;
import com.old.silence.content.domain.repository.LiveRoomRepository;
import com.old.silence.content.infrastructure.persistence.dao.LiveRoomDao;

import java.math.BigInteger;
import java.util.Optional;

/**
* LiveRoom仓储实现
*/
@Repository
public class LiveRoomMyBatisRepository implements LiveRoomRepository {
    private final LiveRoomDao liveRoomDao;

    public LiveRoomMyBatisRepository(LiveRoomDao liveRoomDao) {
        this.liveRoomDao = liveRoomDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return liveRoomDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return liveRoomDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(LiveRoom liveRoom) {
        return liveRoomDao.insert(liveRoom);
    }

    @Override
    public int update(LiveRoom liveRoom) {
        return liveRoomDao.update(liveRoom);
    }

    @Override
    public int deleteById(BigInteger id) {
        return liveRoomDao.deleteById(id);
    }
}