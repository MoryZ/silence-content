package com.old.silence.content.infrastructure.persistence;

import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.model.MarketingRule;
import com.old.silence.content.domain.repository.MarketingRuleRepository;
import com.old.silence.content.infrastructure.persistence.dao.MarketingRuleDao;

import java.math.BigInteger;
import java.util.List;

@Repository
public class MarketingRuleMyBatisRepository implements MarketingRuleRepository {

    private final MarketingRuleDao marketingRuleDao;

    public MarketingRuleMyBatisRepository(MarketingRuleDao marketingRuleDao) {
        this.marketingRuleDao = marketingRuleDao;
    }

    @Override
    public <T> List<T> findAllById(Iterable<BigInteger> ids, Class<T> projectionClass) {
        return marketingRuleDao.findAllById(ids, projectionClass);
    }

    @Override
    public int create(MarketingRule marketingRule) {
        return marketingRuleDao.insert(marketingRule);
    }
}
