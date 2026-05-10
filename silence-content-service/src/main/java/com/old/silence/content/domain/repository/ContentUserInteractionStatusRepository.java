package com.old.silence.content.domain.repository;

import com.old.silence.content.domain.enums.InteractionType;
import com.old.silence.content.domain.enums.ResourceType;
import com.old.silence.content.domain.model.ContentUserInteractionStatus;

import java.math.BigInteger;
import java.util.Optional;

/**
 * ContentUserInteractionStatus仓储接口
 */
public interface ContentUserInteractionStatusRepository {

    <T> Optional<T> findByUserIdAndResourceIdAndResourceTypeAndInteractionType(BigInteger userId,
                                                                               BigInteger resourceId,
                                                                               ResourceType resourceType,
                                                                               InteractionType interactionType,
                                                                               Class<T> projectionType);

    boolean existsByUserIdAndResourceIdAndResourceTypeAndInteractionType(BigInteger userId,
                                                                         BigInteger resourceId,
                                                                         ResourceType resourceType,
                                                                         InteractionType interactionType);

    int create(ContentUserInteractionStatus contentUserInteractionStatus);

    int deleteByUserIdAndResourceIdAndResourceTypeAndInteractionType(BigInteger userId,
                                                                     BigInteger resourceId,
                                                                     ResourceType resourceType,
                                                                     InteractionType interactionType);

    long countByResourceIdAndResourceTypeAndInteractionType(BigInteger resourceId,
                                                            ResourceType resourceType,
                                                            InteractionType interactionType);
}