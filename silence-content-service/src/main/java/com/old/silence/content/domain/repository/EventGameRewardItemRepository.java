package com.old.silence.content.domain.repository;

import com.old.silence.content.domain.model.EventGameRewardItem;

import java.math.BigInteger;
import java.util.List;

/**
 * @author moryzang
 */
public interface EventGameRewardItemRepository {
    <T> List<T> findByEventGameId(BigInteger eventGameId, Class<T> projectionType);

    int bulkReplace(BigInteger eventGameId, List<EventGameRewardItem> eventGameRewardItems);

    int bulkCreate(List<EventGameRewardItem> eventGameRewardItems);
}
