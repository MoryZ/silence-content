package com.old.silence.content.infrastructure.persistence;

import org.springframework.stereotype.Repository;


import com.old.silence.content.domain.model.MarketingEvent;
import com.old.silence.content.domain.repository.MarketingEventRepository;
import com.old.silence.content.infrastructure.persistence.dao.MarketingEventDao;

import java.math.BigInteger;
import java.util.Optional;


/**
 * @author: hexian004
 * @description: 营销活动表
 * @createDate: 2023-01-31 15:22:02.942 周二
 */
@Repository
public class MarketingEventMyBatisRepository implements MarketingEventRepository {

    private final MarketingEventDao marketingEventDao;


    public MarketingEventMyBatisRepository(MarketingEventDao marketingEventDao) {
        this.marketingEventDao = marketingEventDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return marketingEventDao.findById(id, projectionType);
    }

    @Override
    public int create(MarketingEvent marketingEvent) {
        return marketingEventDao.insert(marketingEvent);
    }

    @Override
    public int update(MarketingEvent marketingEvent) {
        return marketingEventDao.update(marketingEvent);
    }

}
