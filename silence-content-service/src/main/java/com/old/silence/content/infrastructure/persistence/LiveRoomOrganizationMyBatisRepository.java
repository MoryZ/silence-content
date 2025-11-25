package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.model.LiveRoomOrganization;
import com.old.silence.content.domain.repository.LiveRoomOrganizationRepository;
import com.old.silence.content.infrastructure.persistence.dao.LiveRoomOrganizationDao;

import java.math.BigInteger;
import java.util.Optional;

/**
* LiveRoomOrganization仓储实现
*/
@Repository
public class LiveRoomOrganizationMyBatisRepository implements LiveRoomOrganizationRepository {
    private final LiveRoomOrganizationDao liveRoomOrganizationDao;

    public LiveRoomOrganizationMyBatisRepository(LiveRoomOrganizationDao liveRoomOrganizationDao) {
        this.liveRoomOrganizationDao = liveRoomOrganizationDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return liveRoomOrganizationDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return liveRoomOrganizationDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(LiveRoomOrganization liveRoomOrganization) {
        return liveRoomOrganizationDao.insert(liveRoomOrganization);
    }

    @Override
    public int update(LiveRoomOrganization liveRoomOrganization) {
        return liveRoomOrganizationDao.update(liveRoomOrganization);
    }

    @Override
    public int deleteById(BigInteger id) {
        return liveRoomOrganizationDao.deleteById(id);
    }
}