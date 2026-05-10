package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.InteractionType;
import com.old.silence.content.domain.enums.ResourceType;
import com.old.silence.content.api.vo.StatsVo;
import com.old.silence.content.domain.model.ContentInteractionLog;
import com.old.silence.content.domain.repository.ContentInteractionLogRepository;
import com.old.silence.content.infrastructure.persistence.dao.ContentInteractionLogDao;
import com.old.silence.content.infrastructure.persistence.dao.support.PoetryAggregationDao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * PoetryUserFavorite仓储实现
 */
@Repository
public class ContentInteractionLogMyBatisRepository implements ContentInteractionLogRepository {
    private final ContentInteractionLogDao contentInteractionLogDao;
    private final PoetryAggregationDao poetryAggregationDao;

    public ContentInteractionLogMyBatisRepository(ContentInteractionLogDao contentInteractionLogDao,
                                                  PoetryAggregationDao poetryAggregationDao) {
        this.contentInteractionLogDao = contentInteractionLogDao;
        this.poetryAggregationDao = poetryAggregationDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return contentInteractionLogDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return contentInteractionLogDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public <T> List<T> findByResourceIdAndResourceTypeAndInteractionType(BigInteger resourceId,
                                                                         ResourceType resourceType,
                                                                         InteractionType interactionType,
                                                                         Class<T> projectionType) {
        return contentInteractionLogDao.findByResourceIdAndResourceTypeAndInteractionType(resourceId, resourceType, interactionType, projectionType);
    }

    @Override
    public <T> List<T> findByUserIdAndInteractionType(BigInteger userId,
                                                      InteractionType interactionType,
                                                      Class<T> projectionType) {
        return contentInteractionLogDao.findByUserIdAndInteractionType(userId, interactionType, projectionType);
    }

    @Override
    public <T> List<T> findByFromUserIdAndInteractionType(BigInteger fromUserId,
                                                          InteractionType interactionType,
                                                          Class<T> projectionType) {
        return contentInteractionLogDao.findByFromUserIdAndInteractionType(fromUserId, interactionType, projectionType);
    }

    @Override
    public <T> List<T> findByParentId(BigInteger parentId, Class<T> projectionType) {
        return contentInteractionLogDao.findByParentId(parentId, projectionType);
    }

    @Override
    public <T> List<T> findByRootId(BigInteger rootId, Class<T> projectionType) {
        return contentInteractionLogDao.findByRootId(rootId, projectionType);
    }

    @Override
    public long countByResourceIdAndResourceTypeAndInteractionType(BigInteger resourceId,
                                                                   ResourceType resourceType,
                                                                   InteractionType interactionType) {
        return contentInteractionLogDao.countByResourceIdAndResourceTypeAndInteractionType(resourceId, resourceType, interactionType);
    }

    @Override
    public List<StatsVo> findFavoriteTop5() {
        return poetryAggregationDao.findFavoriteTop5();
    }

    @Override
    public int create(ContentInteractionLog contentInteractionLog) {
        return contentInteractionLogDao.insert(contentInteractionLog);
    }

    @Override
    public int update(ContentInteractionLog contentInteractionLog) {
        return contentInteractionLogDao.update(contentInteractionLog);
    }

    @Override
    public int deleteById(BigInteger id) {
        return contentInteractionLogDao.deleteById(id);
    }
}