package com.old.silence.content.infrastructure.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


import com.old.silence.content.domain.model.EventGameRewardItem;
import com.old.silence.content.domain.repository.EventGameRewardItemRepository;
import com.old.silence.content.infrastructure.persistence.dao.EventGameRewardItemDao;

import java.math.BigInteger;
import java.util.List;


@Repository
public class EventGameRewardItemMyBatisRepository implements EventGameRewardItemRepository {

    private static final Logger log = LoggerFactory.getLogger(EventGameRewardItemMyBatisRepository.class);

    private final EventGameRewardItemDao eventGameRewardItemDao;

    public EventGameRewardItemMyBatisRepository(EventGameRewardItemDao eventGameRewardItemDao) {
        this.eventGameRewardItemDao = eventGameRewardItemDao;
    }


    @Override
    public int bulkReplace(BigInteger eventGameId, List<EventGameRewardItem> eventGameRewardItems) {
        eventGameRewardItemDao.deleteByEventGameId(eventGameId);
        return eventGameRewardItemDao.insertAll(eventGameRewardItems);
    }

    @Override
    public int bulkCreate(List<EventGameRewardItem> eventGameRewardItems) {
        return eventGameRewardItemDao.insertAll(eventGameRewardItems);
    }


    @Override
    public <T> List<T> findByEventGameId(BigInteger eventGameId, Class<T> projectionType) {
        return eventGameRewardItemDao.findByEventGameId(eventGameId, projectionType);
    }

}
