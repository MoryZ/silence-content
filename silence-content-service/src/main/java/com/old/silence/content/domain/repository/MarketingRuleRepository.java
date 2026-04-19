package com.old.silence.content.domain.repository;

import com.old.silence.content.domain.model.MarketingRule;

import java.math.BigInteger;
import java.util.List;

/**
 * @author moryzang
 */
public interface MarketingRuleRepository {
    <T> List<T> findAllById(Iterable<BigInteger> idList, Class<T> projectionType);

    int create(MarketingRule marketingRule);
}
