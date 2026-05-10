package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.enums.InteractionType;
import com.old.silence.content.domain.enums.ResourceType;
import com.old.silence.content.domain.model.ContentInteractionAccumulation;
import com.old.silence.data.jdbc.repository.JdbcRepository;
import com.old.silence.data.jdbc.repository.query.Modifying;
import com.old.silence.data.jdbc.repository.query.Query;

import java.math.BigInteger;
import java.util.Optional;

/**
 * @author moryzang
 */
public interface ContentInteractionAccumulationDao extends JdbcRepository<ContentInteractionAccumulation, BigInteger> {

    <T> Optional<T> findByResourceIdAndResourceTypeAndType(BigInteger resourceId,
							   ResourceType resourceType,
							   InteractionType type,
							   Class<T> projectionType);

    @Query("UPDATE content_interaction_accumulation " +
	    "SET accumulation = accumulation + :delta " +
	    "WHERE resource_id = :resourceId AND resource_type = :resourceType AND type = :type")
    @Modifying
    int increaseAccumulationByResourceIdAndResourceTypeAndType(BigInteger delta,
							       BigInteger resourceId,
							       ResourceType resourceType,
							       InteractionType type);

    @Query("INSERT INTO content_interaction_accumulation(resource_id, resource_type, type, accumulation) " +
	    "VALUES (:resourceId, :resourceType, :type, :delta) " +
	    "ON DUPLICATE KEY UPDATE accumulation = accumulation + VALUES(accumulation)")
    @Modifying
    int upsertAccumulationByResourceIdAndResourceTypeAndType(BigInteger resourceId,
							     ResourceType resourceType,
							     InteractionType type,
							     BigInteger delta);
}
