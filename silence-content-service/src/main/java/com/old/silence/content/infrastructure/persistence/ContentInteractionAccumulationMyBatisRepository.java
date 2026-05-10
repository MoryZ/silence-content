package com.old.silence.content.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.enums.InteractionType;
import com.old.silence.content.domain.enums.ResourceType;
import com.old.silence.content.domain.model.ContentInteractionAccumulation;
import com.old.silence.content.domain.repository.ContentInteractionAccumulationRepository;
import com.old.silence.content.infrastructure.persistence.dao.ContentInteractionAccumulationDao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;


@Repository
public class ContentInteractionAccumulationMyBatisRepository implements ContentInteractionAccumulationRepository {
    private final ContentInteractionAccumulationDao contentInteractionAccumulationDao;


    public ContentInteractionAccumulationMyBatisRepository(ContentInteractionAccumulationDao contentInteractionAccumulationDao) {
        this.contentInteractionAccumulationDao = contentInteractionAccumulationDao;
    }

    @Override
    public int bulkCreate(List<ContentInteractionAccumulation> contentInteractionAccumulations) {
        return contentInteractionAccumulationDao.insertAll(contentInteractionAccumulations);
    }

    @Override
    public <T> Optional<T> findByResourceIdAndResourceTypeAndType(BigInteger resourceId,
                                                                   ResourceType resourceType,
                                                                   InteractionType type,
                                                                   Class<T> projectionType) {
        return contentInteractionAccumulationDao.findByResourceIdAndResourceTypeAndType(resourceId, resourceType, type, projectionType);
    }

    @Override
    public int increaseAccumulationByResourceIdAndResourceTypeAndType(BigInteger delta,
                                                                      BigInteger resourceId,
                                                                      ResourceType resourceType,
                                                                      InteractionType type) {
        return contentInteractionAccumulationDao.increaseAccumulationByResourceIdAndResourceTypeAndType(delta, resourceId, resourceType, type);
    }

    @Override
    public int upsertAccumulationByResourceIdAndResourceTypeAndType(BigInteger resourceId,
                                                                    ResourceType resourceType,
                                                                    InteractionType type,
                                                                    BigInteger delta) {
        return contentInteractionAccumulationDao.upsertAccumulationByResourceIdAndResourceTypeAndType(resourceId, resourceType, type, delta);
    }
}
