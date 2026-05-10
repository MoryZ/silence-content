package com.old.silence.content.domain.repository;

import com.old.silence.content.domain.enums.InteractionType;
import com.old.silence.content.domain.enums.ResourceType;
import com.old.silence.content.domain.model.ContentInteractionAccumulation;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;


/**
 * @author moryzang
 */
public interface ContentInteractionAccumulationRepository {

    int bulkCreate(List<ContentInteractionAccumulation> contentInteractionAccumulations);

    <T> Optional<T> findByResourceIdAndResourceTypeAndType(BigInteger resourceId,
                                                           ResourceType resourceType,
                                                           InteractionType type,
                                                           Class<T> projectionType);

    int increaseAccumulationByResourceIdAndResourceTypeAndType(BigInteger delta,
                                                               BigInteger resourceId,
                                                               ResourceType resourceType,
                                                               InteractionType type);

    int upsertAccumulationByResourceIdAndResourceTypeAndType(BigInteger resourceId,
                                                             ResourceType resourceType,
                                                             InteractionType type,
                                                             BigInteger delta);
}
