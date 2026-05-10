package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.enums.InteractionType;
import com.old.silence.content.domain.enums.ResourceType;
import com.old.silence.content.domain.model.ContentInteractionLog;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * ContentInteractionLog数据访问接口
 */
public interface ContentInteractionLogDao extends JdbcRepository<ContentInteractionLog, BigInteger> {

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

}