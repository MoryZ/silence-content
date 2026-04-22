package com.old.silence.content.domain.repository;

import com.old.silence.content.domain.model.EventGame;

import java.math.BigInteger;
import java.util.Optional;

/**
 * @author moryzang
 */
public interface EventGameRepository {
    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    int createEventGameWithReward(EventGame eventGame);
}
