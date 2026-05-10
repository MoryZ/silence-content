package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.enums.InteractionType;
import com.old.silence.content.domain.enums.ResourceType;
import com.old.silence.content.domain.model.ContentUserInteractionStatus;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.Optional;

/**
 * ContentUserInteractionStatus数据访问接口
 */
public interface ContentUserInteractionStatusDao extends JdbcRepository<ContentUserInteractionStatus, BigInteger> {

	<T> Optional<T> findByUserIdAndResourceIdAndResourceTypeAndInteractionType(BigInteger userId,
																				BigInteger resourceId,
																				ResourceType resourceType,
																				InteractionType interactionType,
																				Class<T> projectionType);

	boolean existsByUserIdAndResourceIdAndResourceTypeAndInteractionType(BigInteger userId,
																		  BigInteger resourceId,
																		  ResourceType resourceType,
																		  InteractionType interactionType);

	int deleteByUserIdAndResourceIdAndResourceTypeAndInteractionType(BigInteger userId,
																	  BigInteger resourceId,
																	  ResourceType resourceType,
																	  InteractionType interactionType);

	long countByResourceIdAndResourceTypeAndInteractionType(BigInteger resourceId,
															ResourceType resourceType,
															InteractionType interactionType);

}