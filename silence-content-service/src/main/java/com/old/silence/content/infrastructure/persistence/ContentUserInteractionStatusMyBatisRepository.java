package com.old.silence.content.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.InteractionType;
import com.old.silence.content.domain.enums.ResourceType;
import com.old.silence.content.domain.model.ContentUserInteractionStatus;
import com.old.silence.content.domain.repository.ContentUserInteractionStatusRepository;
import com.old.silence.content.infrastructure.persistence.dao.ContentUserInteractionStatusDao;

import java.math.BigInteger;
import java.util.Optional;

/**
 * ContentUserInteractionStatus仓储实现
 */
@Repository
public class ContentUserInteractionStatusMyBatisRepository implements ContentUserInteractionStatusRepository {

    private final ContentUserInteractionStatusDao contentUserInteractionStatusDao;

    public ContentUserInteractionStatusMyBatisRepository(ContentUserInteractionStatusDao contentUserInteractionStatusDao) {
        this.contentUserInteractionStatusDao = contentUserInteractionStatusDao;
    }

    @Override
    public <T> Optional<T> findByUserIdAndResourceIdAndResourceTypeAndInteractionType(BigInteger userId,
                                                                                      BigInteger resourceId,
                                                                                      ResourceType resourceType,
                                                                                      InteractionType interactionType,
                                                                                      Class<T> projectionType) {
        return contentUserInteractionStatusDao.findByUserIdAndResourceIdAndResourceTypeAndInteractionType(userId, resourceId, resourceType, interactionType, projectionType);
    }

    @Override
    public boolean existsByUserIdAndResourceIdAndResourceTypeAndInteractionType(BigInteger userId,
                                                                                BigInteger resourceId,
                                                                                ResourceType resourceType,
                                                                                InteractionType interactionType) {
        return contentUserInteractionStatusDao.existsByUserIdAndResourceIdAndResourceTypeAndInteractionType(userId, resourceId, resourceType, interactionType);
    }

    @Override
    public int create(ContentUserInteractionStatus contentUserInteractionStatus) {
        return contentUserInteractionStatusDao.insert(contentUserInteractionStatus);
    }

    @Override
    public int deleteByUserIdAndResourceIdAndResourceTypeAndInteractionType(BigInteger userId,
                                                                            BigInteger resourceId,
                                                                            ResourceType resourceType,
                                                                            InteractionType interactionType) {
        return contentUserInteractionStatusDao.deleteByUserIdAndResourceIdAndResourceTypeAndInteractionType(userId, resourceId, resourceType, interactionType);
    }

    @Override
    public long countByResourceIdAndResourceTypeAndInteractionType(BigInteger resourceId,
                                                                   ResourceType resourceType,
                                                                   InteractionType interactionType) {
        return contentUserInteractionStatusDao.countByResourceIdAndResourceTypeAndInteractionType(resourceId, resourceType, interactionType);
    }
}