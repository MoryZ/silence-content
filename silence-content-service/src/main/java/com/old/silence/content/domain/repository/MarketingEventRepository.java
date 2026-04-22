package com.old.silence.content.domain.repository;

import com.old.silence.content.domain.model.MarketingEvent;

import java.math.BigInteger;
import java.util.Optional;

/**
 * @author moryzang
 */
public interface MarketingEventRepository {
    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    int create(MarketingEvent marketingEvent);

    int update(MarketingEvent marketingEvent);
}
