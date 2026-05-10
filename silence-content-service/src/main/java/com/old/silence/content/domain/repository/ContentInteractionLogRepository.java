package com.old.silence.content.domain.repository;

import com.old.silence.content.domain.enums.InteractionType;
import com.old.silence.content.domain.enums.ResourceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.api.vo.StatsVo;
import com.old.silence.content.domain.model.ContentInteractionLog;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * ContentInteractionLog仓储接口
 */
public interface ContentInteractionLogRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    <T> List<T> findByResourceIdAndResourceTypeAndInteractionType(BigInteger resourceId,
                                                                  ResourceType resourceType,
                                                                  InteractionType interactionType,
                                                                  Class<T> projectionType);

    <T> List<T> findByUserIdAndInteractionType(BigInteger userId,
                                               InteractionType interactionType,
                                               Class<T> projectionType);

    <T> List<T> findByFromUserIdAndInteractionType(BigInteger fromUserId,
                                                   InteractionType interactionType,
                                                   Class<T> projectionType);

    <T> List<T> findByParentId(BigInteger parentId, Class<T> projectionType);

    <T> List<T> findByRootId(BigInteger rootId, Class<T> projectionType);

    long countByResourceIdAndResourceTypeAndInteractionType(BigInteger resourceId,
                                                            ResourceType resourceType,
                                                            InteractionType interactionType);

    List<StatsVo> findFavoriteTop5();

    int create(ContentInteractionLog contentInteractionLog);

    int update(ContentInteractionLog contentInteractionLog);

    int deleteById(BigInteger id);

}