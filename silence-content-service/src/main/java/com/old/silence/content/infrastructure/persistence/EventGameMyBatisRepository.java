package com.old.silence.content.infrastructure.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.old.silence.content.domain.enums.EventGameRewardItemClaimType;
import com.old.silence.content.domain.model.EventGame;
import com.old.silence.content.domain.model.EventGameRewardItem;
import com.old.silence.content.domain.repository.EventGameRepository;
import com.old.silence.content.domain.repository.EventGameRewardItemRepository;
import com.old.silence.content.domain.repository.MarketingRuleRepository;
import com.old.silence.content.infrastructure.persistence.dao.EventGameDao;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.data.jdbc.util.JdbcUtils;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * @author: hexian004
 */
@Repository
public class EventGameMyBatisRepository implements EventGameRepository {

    private static final Logger logger = LoggerFactory.getLogger(EventGameMyBatisRepository.class);

    private final EventGameDao eventGameDao;


    private final MarketingRuleRepository marketingRuleRepository;

    private final EventGameRewardItemRepository eventGameRewardItemRepository;

    public EventGameMyBatisRepository(EventGameDao eventGameDao,
                                      MarketingRuleRepository marketingRuleRepository,
                                      EventGameRewardItemRepository eventGameRewardItemRepository) {
        this.eventGameDao = eventGameDao;
        this.marketingRuleRepository=marketingRuleRepository;
        this.eventGameRewardItemRepository=eventGameRewardItemRepository;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return eventGameDao.findById(id, projectionType);
    }



    @Override
    @Transactional
    public int createEventGameWithReward(EventGame eventGame) {

        JdbcUtils.saveCascadeEntity(eventGame, EventGame::getEntryRule, marketingRuleRepository::create,
                EventGame::setEntryRuleId);

        var rowsAffected = eventGameDao.insert(eventGame);

        // 保存奖品列表
        var eventGameRewardItems = eventGame.getEventGameRewardItems();
        saveEventGameRewardItems(eventGame.getId(), eventGameRewardItems);
        return rowsAffected;
    }

    private void saveEventGameRewardItems(BigInteger eventGameId, List<EventGameRewardItem> eventGameRewardItems) {
        if (CollectionUtils.isEmpty(eventGameRewardItems)) {
            return;
        }

        for (var eventGameRewardItem : eventGameRewardItems) {
            eventGameRewardItem.setEventGameId(eventGameId);
            eventGameRewardItem.setClaimType(EventGameRewardItemClaimType.AUTOMATIC);
            JdbcUtils.saveCascadeEntity(eventGameRewardItem, EventGameRewardItem::getRewardRule, marketingRuleRepository::create,
                    EventGameRewardItem::setRewardRuleId);
        }
        eventGameRewardItemRepository.bulkCreate(eventGameRewardItems);
    }


}
