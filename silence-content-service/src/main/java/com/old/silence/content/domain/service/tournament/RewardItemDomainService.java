package com.old.silence.content.domain.service.tournament;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.old.silence.content.api.vo.RewardItemView;
import com.old.silence.content.domain.common.BizErrorCode;
import com.old.silence.content.domain.common.MarketingBizException;
import com.old.silence.content.domain.enums.EventGameType;
import com.old.silence.content.domain.enums.MarketingEventStatus;
import com.old.silence.content.domain.enums.RewardItemStatus;
import com.old.silence.content.domain.enums.RewardItemType;
import com.old.silence.content.domain.model.EventGameRewardItem;
import com.old.silence.content.domain.model.MarketingEvent;
import com.old.silence.content.domain.model.RewardItem;
import com.old.silence.content.domain.repository.EventGameRepository;
import com.old.silence.content.domain.repository.MarketingEventRepository;
import com.old.silence.content.domain.service.EventGameRewardItemDomainService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class RewardItemDomainService {

    /*private static final Logger log = LoggerFactory.getLogger(RewardItemDomainService.class);
    private final RewardItemRepository rewardItemRepository;

    private final EventLotteryRepository eventLotteryRepository;

    private final MarketingEventRepository marketingEventRepository;

    private final EventGameRepository eventGameRepository;

    private final RewardPoolRepository rewardPoolRepository;

    @Autowired
    private EventGameRewardItemDomainService eventGameRewardItemDomainService;

    private final RedisClient redisClient;

    private final InventoryInstanceLogRepository inventoryInstanceLogRepository;

    private final ConfigCenter configCenter;

    public RewardItemDomainService(RewardItemRepository rewardItemRepository, EventLotteryRepository eventLotteryRepository, MarketingEventRepository marketingEventRepository, EventGameRepository eventGameRepository, RewardPoolRepository rewardPoolRepository, RedisClient redisClient, InventoryInstanceLogRepository inventoryInstanceLogRepository, ConfigCenter configCenter) {
        this.rewardItemRepository = rewardItemRepository;
        this.eventLotteryRepository = eventLotteryRepository;
        this.marketingEventRepository = marketingEventRepository;
        this.eventGameRepository = eventGameRepository;
        this.rewardPoolRepository = rewardPoolRepository;
        this.redisClient = redisClient;
        this.inventoryInstanceLogRepository = inventoryInstanceLogRepository;
        this.configCenter = configCenter;
    }

    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return rewardItemRepository.findById(id, projectionType);
    }

    public <T> List<T> findByIds(Set<BigInteger> ids, Class<T> projectionType) {
        return rewardItemRepository.findByIdIn(ids, projectionType);
    }

    public Optional<RewardItem> findByIdWithCache(BigInteger id) {
        return Optional.of(rewardItemRepository.findByIdWithCache(id));
    }

    public <T> Optional<T> findWithWriteLockById(BigInteger id, Class<T> projectionType) {
        return rewardItemRepository.findWithWriteLockById(id, projectionType);
    }

    public boolean existsById(BigInteger id) {
        return rewardItemRepository.existsById(id);
    }

    public <T> List<T> findAllById(Iterable<BigInteger> idList, Class<T> projectionType) {
        return rewardItemRepository.findAllById(idList, projectionType);
    }

    public <T> Page<T> queryPage(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return rewardItemRepository.queryPage(criteria, pageable, projectionType);
    }

    public <T> List<T> query(Criteria criteria, Class<T> projectionType) {
        return rewardItemRepository.query(criteria, projectionType);
    }

    public <T> List<T> findByReferenceIdAndType(String referenceId, RewardItemType type, Class<T> projectionType) {
        return rewardItemRepository.findByReferenceIdAndType(referenceId, type, projectionType);
    }

    @Transactional
    public void create(RewardItem rewardItem) {
        rewardItem.setStatus(RewardItemStatus.ONLINE);
        rewardItem.setInventoryQuantity(rewardItem.getQuantity());
        rewardItemRepository.create(rewardItem);
        // 创建奖品缓存
        var rewardItemInventoryCacheKey = RedisKeyUtils.getRewardItemInventoryCacheKey(rewardItem.getId());
        var rewardItemTotalKey=RedisKeyUtils.getRewardItemTotalKey(rewardItem.getId());
        if (RewardItemType.THANKS_REWARD.equals(rewardItem.getType())){
            rewardItem.setQuantity(-1L);
        }
        var inventoryInstanceLogs = new ArrayList<InventoryInstanceLog>();
        inventoryInstanceLogs.add(bulidInventoryInstanceLog(rewardItemInventoryCacheKey,rewardItem));
        inventoryInstanceLogRepository.bulkCreate(inventoryInstanceLogs);
        redisClient.set(rewardItemInventoryCacheKey,String.valueOf(rewardItem.getQuantity()));
        redisClient.set(rewardItemTotalKey,String.valueOf(rewardItem.getQuantity()));
    }

    private InventoryInstanceLog bulidInventoryInstanceLog(String rewardKey,RewardItem rewardItem) {
        // 创建奖品库存实例数据
        InventoryInstanceLog inventoryInstanceLog = new InventoryInstanceLog();
        inventoryInstanceLog.setInstanceKey(rewardKey);
        inventoryInstanceLog.setStockChangeValue(rewardItem.getQuantity().intValue());
        inventoryInstanceLog.setStockChangeType(StockChangeType.INIT);
        return inventoryInstanceLog;
    }

    public BigInteger sync(RewardItem rewardItem) {

        //查询奖品
        var referenceId = rewardItem.getReferenceId();
        RewardItemQueryDTO queryDTO = new RewardItemQueryDTO();
        queryDTO.setReferenceId(referenceId);
        queryDTO.setTypes(new Integer[]{RewardItemType.AGENT_HELPER_EQUITIES.getValue().intValue()});
        var rewardItemView = rewardItemRepository.query(queryDTO.toCriteria(), RewardItemView.class).stream().findFirst();
        if (rewardItemView.isPresent()) {
            var rewardItemId = rewardItemView.get().getId();
            rewardItem.setId(rewardItemId);
            //更新奖品
            update(rewardItem);
        } else {
            //新增奖品
            create(rewardItem);
        }

        return rewardItem.getId();
    }

    @Transactional
    public int update(RewardItem rewardItem) {
        // 编辑时总数量与可用数量的联动逻辑暂时不处理,直接用总数量覆盖,后续产品确认联动逻辑再优化
        //Optional<RewardItemDomainView> rewardItemOptional = rewardItemRepository.findById(rewardItem.getId(),
        //        RewardItemDomainView.class);
        //var inventory = rewardItemOptional.map(RewardItemDomainView::getInventoryQuantity)
        //        .orElseThrow(() -> new MarketingBizException(BizErrorCode.INVALID_REWARD_ITEM_ID));
        //rewardItem.setInventoryQuantity(rewardItem.getQuantity());
        // 默认本期只修改CC配置的活动
        boolean isUsedByLottery =false;
        var eventGameIds=configCenter.getDrawLotteryOptimizeEventGameIds();
        var rewardPoolOnlyIdAndItemDomainViews = rewardPoolRepository.findByEventGameIdIn(eventGameIds, RewardPoolOnlyIdAndItemDomainView.class);
        Set<BigInteger> useRewardItemIds = new HashSet<>();
        if (CollectionUtils.isNotEmpty(rewardPoolOnlyIdAndItemDomainViews)) {
            for (RewardPoolOnlyIdAndItemDomainView rewardPoolOnlyIdAndItemDomainView:rewardPoolOnlyIdAndItemDomainViews){
                useRewardItemIds.addAll(rewardPoolOnlyIdAndItemDomainView.getRewardPoolItems().stream().map(RewardPoolItemOnlyRuleAndRewardDomainView::getRewardItemId).collect(Collectors.toSet()));
            }
        }
        if(CollectionUtils.isNotEmpty(useRewardItemIds)){
            if(useRewardItemIds.contains(rewardItem.getId())){
                isUsedByLottery=true;
            }
        }
        // 删除活动树
        var eventLotteries = eventLotteryRepository.findByRewardPoolsRewardPoolItemsRewardItemId(rewardItem.getId());
        Set<BigInteger> gameIds = eventLotteries.stream().map(EventLottery::getEventGameId).collect(Collectors.toSet());
        var events = marketingEventRepository.findByEventGamesIdInAndStatus(gameIds, MarketingEventStatus.PUBLISHED);
        if (CollectionUtils.isNotEmpty(events)) {
            for (MarketingEvent event:events){
                redisClient.delete(RedisKeyUtils.getMarketingEventTreeKey(event.getId()));
            }
        }

        if(!isUsedByLottery
                &&RewardItemType.AGENT_HELPER_EQUITIES.equals(rewardItem.getType())){
            // 创建奖品缓存
            var rewardItemInventoryCacheKey = RedisKeyUtils.getRewardItemInventoryCacheKey(rewardItem.getId());
            // 获取缓存下奖品剩余库存
            var inventory = redisClient.get(rewardItemInventoryCacheKey);
            log.info("根据缓存奖品key查询缓存value,key={},value={}",rewardItemInventoryCacheKey,inventory);
            // 1. 无限制 改为 有限制场景
            if (inventory < 0 && rewardItem.getQuantity() >= 0) {
                rewardItem.setInventoryQuantity(rewardItem.getQuantity());
            }
            // 2. 无限制 改为 无限制场景
            if (inventory < 0 && rewardItem.getQuantity() < 0) {
                rewardItem.setInventoryQuantity(rewardItem.getQuantity());
            }
            // 3. 有限制 改为 无限制场景
            if (inventory >= 0 && rewardItem.getQuantity() < 0) {
                rewardItem.setInventoryQuantity(rewardItem.getQuantity());
            }
            // 4. 有限制 改为 有限制场景
            if (inventory >= 0 && rewardItem.getQuantity() >= 0) {
                long diff = rewardItem.getQuantity() - inventory;
                if (diff >= 0) {
                    // 增加库存的场景
                    rewardItem.setInventoryQuantity(inventory + diff);
                } else {
                    // 减少库存的场景
                    if (Math.abs(diff) > inventory) {
                        throw new MarketingBizException(BizErrorCode.REWARD_ITEM_STOCK_UPDATE_INVALID_PARAMS);
                    }
                    rewardItem.setInventoryQuantity(inventory-Math.abs(diff));
                }
            }
            redisClient.set(rewardItemInventoryCacheKey,String.valueOf(rewardItem.getQuantity()));
            //
            // 创建奖品库存实例数据
            var inventoryInstanceLogs=inventoryInstanceLogRepository.findByInstanceKey(rewardItemInventoryCacheKey, InventoryInstanceLog.class);
            if(CollectionUtils.isNotEmpty(inventoryInstanceLogs)){
                var integers=inventoryInstanceLogs.stream().map(InventoryInstanceLog::getId).collect(Collectors.toList());
                inventoryInstanceLogRepository.deleteByIds(integers);
                InventoryInstanceLog inventoryInstanceLog = new InventoryInstanceLog();
                inventoryInstanceLog.setInstanceKey(rewardItemInventoryCacheKey);
                inventoryInstanceLog.setStockChangeValue(rewardItem.getQuantity().intValue());
                inventoryInstanceLog.setStockChangeType(StockChangeType.INIT);
                inventoryInstanceLogRepository.create(inventoryInstanceLog);
            }else {
                InventoryInstanceLog inventoryInstanceLog = new InventoryInstanceLog();
                inventoryInstanceLog.setInstanceKey(rewardItemInventoryCacheKey);
                inventoryInstanceLog.setStockChangeValue(rewardItem.getQuantity().intValue());
                inventoryInstanceLog.setStockChangeType(StockChangeType.INIT);
                inventoryInstanceLogRepository.create(inventoryInstanceLog);
            }
        }else if(isUsedByLottery
                &&!RewardItemType.THANKS_REWARD.equals(rewardItem.getType())){
            // 库存更新逻辑
            RewardItem current = rewardItemRepository.findById(rewardItem.getId(), RewardItem.class).orElseThrow(ResourceNotFoundException::new);
            // 1. 无限制 改为 有限制场景
            if (current.getQuantity() < 0 && rewardItem.getQuantity() >= 0) {
                rewardItem.setInventoryQuantity(rewardItem.getQuantity());
            }
            // 3. 有限制 改为 无限制场景
            if (current.getQuantity() >= 0 && rewardItem.getQuantity() < 0) {
                rewardItem.setInventoryQuantity(rewardItem.getQuantity());
            }
            // 4. 有限制 改为 有限制场景
            var rewardItemInventoryCacheKey = RedisKeyUtils.getRewardItemInventoryCacheKey(rewardItem.getId());
            if (current.getQuantity() >= 0 && rewardItem.getQuantity() >= 0) {
                var diff = rewardItem.getQuantity() - current.getQuantity();
                var inventory = current.getInventoryQuantity();
                if(redisClient.hasKey(rewardItemInventoryCacheKey)){
                    inventory = redisClient.get(rewardItemInventoryCacheKey).longValue();
                }
                if (diff >= 0) {
                    // 增加库存的场景
                    rewardItem.setInventoryQuantity(inventory + diff);
                } else {
                    // 减少库存的场景
                    if (Math.abs(diff) > inventory) {
                        throw new MarketingBizException(BizErrorCode.REWARD_ITEM_STOCK_UPDATE_INVALID_PARAMS);
                    }
                    rewardItem.setInventoryQuantity(inventory-Math.abs(diff));
                }
            }
            redisClient.set(rewardItemInventoryCacheKey,String.valueOf(rewardItem.getInventoryQuantity()));
            //
            // 创建奖品库存实例数据
            var inventoryInstanceLogs=inventoryInstanceLogRepository.findByInstanceKey(rewardItemInventoryCacheKey, InventoryInstanceLog.class);
            if(CollectionUtils.isNotEmpty(inventoryInstanceLogs)){
                var integers=inventoryInstanceLogs.stream().map(InventoryInstanceLog::getId).collect(Collectors.toList());
                inventoryInstanceLogRepository.deleteByIds(integers);
                InventoryInstanceLog inventoryInstanceLog = new InventoryInstanceLog();
                inventoryInstanceLog.setInstanceKey(rewardItemInventoryCacheKey);
                inventoryInstanceLog.setStockChangeValue(rewardItem.getInventoryQuantity().intValue());
                inventoryInstanceLog.setStockChangeType(StockChangeType.INIT);
                inventoryInstanceLogRepository.create(inventoryInstanceLog);
            }else {
                InventoryInstanceLog inventoryInstanceLog = new InventoryInstanceLog();
                inventoryInstanceLog.setInstanceKey(rewardItemInventoryCacheKey);
                inventoryInstanceLog.setStockChangeValue(rewardItem.getInventoryQuantity().intValue());
                inventoryInstanceLog.setStockChangeType(StockChangeType.INIT);
                inventoryInstanceLogRepository.create(inventoryInstanceLog);
            }
        }else{
            // 库存更新逻辑
            RewardItem current = rewardItemRepository.findById(rewardItem.getId(), RewardItem.class).orElseThrow(ResourceNotFoundException::new);

            // 1. 无限制 改为 有限制场景
            if (current.getQuantity() < 0 && rewardItem.getQuantity() >= 0) {
                rewardItem.setInventoryQuantity(rewardItem.getQuantity());
            }
            // 2. 无限制 改为 无限制场景
            if (current.getQuantity() < 0 && rewardItem.getQuantity() < 0) {
                rewardItem.setInventoryQuantity(rewardItem.getQuantity());
            }
            // 3. 有限制 改为 无限制场景
            if (current.getQuantity() >= 0 && rewardItem.getQuantity() < 0) {
                rewardItem.setInventoryQuantity(rewardItem.getQuantity());
            }
            // 4. 有限制 改为 有限制场景
            if (current.getQuantity() >= 0 && rewardItem.getQuantity() >= 0) {
                Long diff = rewardItem.getQuantity() - current.getQuantity();
                if (diff >= 0) {
                    // 增加库存的场景
                    rewardItem.setInventoryQuantity(current.getInventoryQuantity() + diff);
                } else {
                    // 减少库存的场景
                    if (Math.abs(diff) > current.getInventoryQuantity()) {
                        throw new MarketingBizException(BizErrorCode.REWARD_ITEM_STOCK_UPDATE_INVALID_PARAMS);
                    }
                    rewardItem.setInventoryQuantity(current.getInventoryQuantity()-Math.abs(diff));
                }
            }
            redisClient.delete(RedisKeyUtils.getRewardItemInventoryCacheKey(rewardItem.getId()));
            // 奖品总库存
            var rewardItemTotalKey = RedisKeyUtils.getRewardItemTotalKey(rewardItem.getId());
            InventoryInstanceLog inventoryInstanceLog = new InventoryInstanceLog();
            inventoryInstanceLog.setStockChangeValue(Integer.valueOf(String.valueOf(rewardItem.getQuantity())));
            if (RewardItemType.THANKS_REWARD.equals(rewardItem.getType())){
                inventoryInstanceLog.setStockChangeValue(-1);
            }
            redisClient.set(rewardItemTotalKey,String.valueOf(inventoryInstanceLog.getStockChangeValue()));
        }
        // 目前奖品没有审核逻辑 暂时都为上线状态
        rewardItem.setStatus(RewardItemStatus.ONLINE);
        var rowsAffected = rewardItemRepository.update(rewardItem);
        // 清除关联的活动缓存
        List<BigInteger> eventIds = queryAllRelationMarketingEventId(rewardItem.getId());
        for (BigInteger eventId : eventIds) {
            redisClient.delete(RedisKeyUtils.getMarketingEventTreeKey(eventId));
        }
        return rowsAffected;
    }

    public int updateName(String name, BigInteger id) {
        return rewardItemRepository.updateNameById(name, id);
    }

    public int deleteById(BigInteger id) {
        // 从奖品->奖池->玩法->活动
        var eventLotteries = eventLotteryRepository.findByRewardPoolsRewardPoolItemsRewardItemId(id);
        Set<BigInteger> gameIds = eventLotteries.stream().map(EventLottery::getEventGameId).collect(Collectors.toSet());
        var events = marketingEventRepository.findByEventGamesIdInAndStatus(gameIds, MarketingEventStatus.PUBLISHED);
        if (CollectionUtils.isNotEmpty(events)) {
            throw new MarketingBizException(BizErrorCode.CANNOT_DELETE_REWARD_ITEM);
        }
        return rewardItemRepository.deleteById(id);
    }

    *//**
     * 查询奖品关联的所有上线中的活动
     *//*
    public List<BigInteger> queryAllRelationMarketingEventId(BigInteger id) {
        // 抽奖玩法关联的活动
        var eventLotteries = eventLotteryRepository.findByRewardPoolsRewardPoolItemsRewardItemId(id);
        Set<BigInteger> gameIds = eventLotteries.stream().map(EventLottery::getEventGameId).collect(Collectors.toSet());
        // 任务流玩法关联的活动
        Set<BigInteger> taskIds = eventGameRewardItemDomainService.findByRewardItemId(id).stream().map(EventGameRewardItem::getEventGameId).collect(Collectors.toSet());
        gameIds.addAll(taskIds);
        return marketingEventRepository.findByEventGamesIdInAndStatus(gameIds, MarketingEventStatus.PUBLISHED).stream().map(MarketingEvent::getId).collect(Collectors.toList());
    }

    public int updateCreatedBy(String createdBy, BigInteger id) {
        return rewardItemRepository.updateCreatedBy(createdBy, id);
    }

    public Set<RewardItemForGoldenKeyDTO> queryRewardItemForGoldenKey(BigInteger id) {
        // 查找活动下刮刮卡玩法
        List<EventGameOnlyIdView> eventGames = eventGameRepository.findByEventIdAndType(id, EventGameType.DIAMOND_SCRATCH, EventGameOnlyIdView.class);
        if (CollectionUtils.isEmpty(eventGames)) {
            return Collections.emptySet();
        }

        var eventGameIds = eventGames.stream().map(EventGameOnlyIdView::getId).collect(Collectors.toList());
        // 根据玩法id查询奖池
        var rewardPoolOnlyIdAndItemDomainViews = rewardPoolRepository.findByEventGameIdIn(eventGameIds, RewardPoolOnlyIdAndItemDomainView.class);
        if (CollectionUtils.isEmpty(rewardPoolOnlyIdAndItemDomainViews)) {
            return Collections.emptySet();
        }

        // 奖品奖池关系构建
        List<RewardPoolItemOnlyRuleAndRewardDomainView> rewardPoolItems = rewardPoolOnlyIdAndItemDomainViews.stream()
                .map(RewardPoolOnlyIdAndItemDomainView::getRewardPoolItems)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(rewardPoolItems)) {
            return Collections.emptySet();
        }
        return getItemForGoldenKeyDTOS(rewardPoolItems);
    }

    private Set<RewardItemForGoldenKeyDTO> getItemForGoldenKeyDTOS(List<RewardPoolItemOnlyRuleAndRewardDomainView> rewardPoolItems) {
        if (rewardPoolItems == null || rewardPoolItems.isEmpty()) {
            return Collections.emptySet();
        }
        return rewardPoolItems.stream()
                .filter(this::shouldProcessItem)
                .map(this::buildRewardItemForGoldenKeyDTO)
                .collect(Collectors.collectingAndThen(Collectors.toCollection(()-> new TreeSet<>(Comparator.comparing(RewardItemForGoldenKeyDTO::getRewardItemId))),TreeSet::new));
    }

    private boolean shouldProcessItem(RewardPoolItemOnlyRuleAndRewardDomainView item) {
        return item != null && item.getRewardItem() != null &&
                !RewardItemType.THANKS_REWARD.equals(item.getRewardItem().getType());
    }

    private RewardItemForGoldenKeyDTO buildRewardItemForGoldenKeyDTO(RewardPoolItemOnlyRuleAndRewardDomainView item) {
        RewardItemForGoldenKeyDTO dto = new RewardItemForGoldenKeyDTO();
        dto.setRewardItemId(item.getRewardItem().getId());
        dto.setRewardItemName(item.getRewardItem().getDisplayName());
        dto.setGrand(Boolean.FALSE);

        if (item.getRewardRule() != null && item.getRewardRule().getContent() instanceof FrequencyRuleDTO) {
            FrequencyRuleDTO frequencyRuleDTO = (FrequencyRuleDTO) item.getRewardRule().getContent();
            if (frequencyRuleDTO.getRewardItemLevel() != null && frequencyRuleDTO.getRewardItemLevel() == 2) {
                dto.setGrand(Boolean.TRUE);
            }
        }
        return dto;
    }

    public void expireConfig(BigInteger id, RewardItemAttributesDomainCommand command) {
        Optional<RewardItem> rewardItemOptional = rewardItemRepository.findById(id, RewardItem.class);
        if(rewardItemOptional.isEmpty()){
            throw new ResourceNotFoundException();
        }
        RewardItem rewardItem = rewardItemOptional.get();
        LotteryChanceAttributes attributes = (LotteryChanceAttributes)rewardItem.getAttributes();
        attributes.setExpireDuration(command.getExpireDuration());
        rewardItemRepository.update(rewardItem);
    }*/
}
