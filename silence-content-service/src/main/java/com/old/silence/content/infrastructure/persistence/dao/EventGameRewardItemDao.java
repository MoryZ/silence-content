package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.EventGameRewardItem;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * @author moryzang
 */
public interface EventGameRewardItemDao extends JdbcRepository<EventGameRewardItem, BigInteger>
{
    int deleteByEventGameId(BigInteger eventGameId);

    <T> List<T> findByEventGameId(BigInteger eventGameId, Class<T> projectionType);
}
