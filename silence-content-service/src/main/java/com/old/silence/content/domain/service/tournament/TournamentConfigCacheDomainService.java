package com.old.silence.content.domain.service.tournament;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.old.silence.autoconfigure.minio.MinioTemplate;
import com.old.silence.content.api.assembler.tournament.TournamentConfigCacheDtoMapper;
import com.old.silence.content.api.assembler.tournament.TournamentConfigVoMapper;
import com.old.silence.content.api.assembler.tournament.TournamentMarketingEventCacheDtoMapper;
import com.old.silence.content.api.assembler.tournament.TournamentRewardItemRuleCacheDtoMapper;
import com.old.silence.content.api.tournament.tournament.dto.TournamentConfigCacheDto;
import com.old.silence.content.api.tournament.tournament.dto.TournamentMarketingEventCacheDto;
import com.old.silence.content.api.tournament.tournament.dto.TournamentRewardItemCacheDto;
import com.old.silence.content.api.tournament.tournament.dto.TournamentRewardItemRuleCacheDto;
import com.old.silence.content.api.tournament.tournament.vo.TournamentConfigVo;
import com.old.silence.content.domain.common.BizErrorCode;
import com.old.silence.content.domain.common.MarketingBizException;
import com.old.silence.content.domain.common.TournamentCacheDurationConstant;
import com.old.silence.content.domain.model.EventGame;
import com.old.silence.content.domain.model.EventGameRewardItem;
import com.old.silence.content.domain.model.MarketingEvent;
import com.old.silence.content.domain.model.MarketingRule;
import com.old.silence.content.domain.model.TournamentConfig;
import com.old.silence.content.domain.repository.tournament.TournamentConfigRepository;
import com.old.silence.content.domain.service.EventGameDomainService;
import com.old.silence.content.domain.service.EventGameRewardItemDomainService;
import com.old.silence.content.domain.service.MarketingEventDomainService;
import com.old.silence.content.domain.service.MarketingRuleDomainService;
import com.old.silence.content.util.TournamentCacheKeyUtils;
import com.old.silence.json.JacksonMapper;
import com.old.silence.json.JacksonUtils;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TournamentConfigCacheDomainService {


    private static final Logger logger = LoggerFactory.getLogger(TournamentConfigCacheDomainService.class);

    private final TournamentConfigRepository tournamentConfigRepository;
    private final MinioTemplate minioTemplate;
    private final MarketingEventDomainService marketingEventDomainService;
    private final EventGameDomainService eventGameDomainService;
    private final RewardItemDomainService rewardItemDomainService;
    private final MarketingRuleDomainService marketingRuleDomainService;
    private final EventGameRewardItemDomainService eventGameRewardItemDomainService;
    private final StringRedisTemplate stringRedisTemplate;
    private final JacksonMapper jacksonMapper;
    private final JacksonMapper jacksonMapperWithType;
    private final TournamentConfigCacheDtoMapper tournamentConfigCacheDtoMapper;
    private final TournamentMarketingEventCacheDtoMapper tournamentMarketingEventCacheDtoMapper;
    private final TournamentRewardItemRuleCacheDtoMapper tournamentRewardItemRuleCacheDtoMapper;
    private final TournamentConfigVoMapper tournamentConfigVoMapper;


    public TournamentConfigCacheDomainService(TournamentConfigRepository tournamentConfigRepository,
                                              MinioTemplate minioTemplate,
                                              MarketingEventDomainService marketingEventDomainService,
                                              EventGameDomainService eventGameDomainService,
                                              RewardItemDomainService rewardItemDomainService,
                                              MarketingRuleDomainService marketingRuleDomainService,
                                              EventGameRewardItemDomainService eventGameRewardItemDomainService,
                                              StringRedisTemplate stringRedisTemplate,
                                              JacksonMapper jacksonMapper,
                                              ObjectMapper objectMapper,
                                              TournamentConfigCacheDtoMapper tournamentConfigCacheDtoMapper,
                                              TournamentMarketingEventCacheDtoMapper tournamentMarketingEventCacheDtoMapper,
                                              TournamentRewardItemRuleCacheDtoMapper tournamentRewardItemRuleCacheDtoMapper,
                                              TournamentConfigVoMapper tournamentConfigVoMapper) {
        this.tournamentConfigRepository = tournamentConfigRepository;
        this.minioTemplate = minioTemplate;
        this.marketingEventDomainService = marketingEventDomainService;
        this.eventGameDomainService = eventGameDomainService;
        this.rewardItemDomainService = rewardItemDomainService;
        this.marketingRuleDomainService = marketingRuleDomainService;
        this.eventGameRewardItemDomainService = eventGameRewardItemDomainService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.jacksonMapper = jacksonMapper;
        this.tournamentConfigCacheDtoMapper = tournamentConfigCacheDtoMapper;
        this.tournamentMarketingEventCacheDtoMapper = tournamentMarketingEventCacheDtoMapper;
        this.tournamentRewardItemRuleCacheDtoMapper = tournamentRewardItemRuleCacheDtoMapper;
        this.tournamentConfigVoMapper = tournamentConfigVoMapper;
        ObjectMapper copiedMapper = objectMapper.copy();
        copiedMapper.setDefaultTyping(JacksonUtils.getDefaultTypeResolverBuilder());
        this.jacksonMapperWithType = new JacksonMapper(copiedMapper);
    }

    public TournamentConfigCacheDto queryTournamentConfig(BigInteger eventGameId) {
        TournamentConfigCacheDto tournamentConfigCacheDto = queryTournamentConfigWithCache(eventGameId);
        if (tournamentConfigCacheDto == null) {
            tournamentConfigCacheDto = queryTournamentConfigFromDb(eventGameId);
        }
        return tournamentConfigCacheDto;
    }


    private TournamentConfigCacheDto queryTournamentConfigWithCache(BigInteger eventGameId) {
        TournamentConfigCacheDto tournamentConfigCacheDto = null;
        String tournamentConfigKey = TournamentCacheKeyUtils.getTournamentConfigKey(eventGameId);
        try {
            tournamentConfigCacheDto = jacksonMapperWithType.fromJson(stringRedisTemplate.opsForValue().get(tournamentConfigKey), TournamentConfigCacheDto.class);
        } catch (Exception ex) {
            logger.warn("从缓存获取赛事配置失败,eventGameId={},error={}", eventGameId, ex.getMessage());
        }
        return tournamentConfigCacheDto;
    }

    private TournamentConfigCacheDto queryTournamentConfigFromDb(BigInteger eventGameId) {
        TournamentConfigCacheDto tournamentConfigCacheDto = null;
        try {
            tournamentConfigCacheDto = buildTournamentConfigDto(eventGameId);
        } catch (Exception ex) {
            logger.warn("从DB构造赛事详情失败,eventGameId={},error={}", eventGameId, ex.getMessage());
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_NOT_EXIST);
        }

        if (null != tournamentConfigCacheDto) {
            try {
                String marketingEventTreeKey = TournamentCacheKeyUtils.getTournamentConfigKey(eventGameId);
                logger.info("DB构造赛事详情放入缓存,eventGameId={},tournamentConfigCacheDto={}", eventGameId, jacksonMapperWithType.toJson(tournamentConfigCacheDto));
                stringRedisTemplate.opsForValue().set(
                        marketingEventTreeKey,
                        jacksonMapperWithType.toJson(tournamentConfigCacheDto),
                        TournamentCacheDurationConstant.TOURNAMENT_CONFIG_DURATION,
                        TimeUnit.DAYS);
            } catch (Exception ex) {
                logger.warn("将活赛事详情存入缓存失败,eventGameId={},error={}", eventGameId, ex.getMessage());
            }
        }
        return tournamentConfigCacheDto;
    }

    private TournamentConfigCacheDto buildTournamentConfigDto(BigInteger eventGameId) {
        Optional<EventGame> eventGameOptional = eventGameDomainService.findById(eventGameId, EventGame.class);
        if (eventGameOptional.isEmpty()) {
            logger.info("not find eventGame,eventGameId:{}", eventGameId);
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_NOT_EXIST);
        }

        Optional<TournamentConfig> tournamentConfigOptional = tournamentConfigRepository.findByEventGameId(eventGameId, TournamentConfig.class);
        if (tournamentConfigOptional.isEmpty()) {
            logger.info("not find tournamentConfig,eventGameId:{}", eventGameId);
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_NOT_EXIST);
        }
        var tournamentConfig = tournamentConfigOptional.get();
        EventGame eventGame = eventGameOptional.get();
        Optional<MarketingEvent> marketingEventOptional = marketingEventDomainService.findById(eventGame.getEventId(), MarketingEvent.class);
        if (marketingEventOptional.isEmpty()) {
            logger.info("not find marketingEvent,eventId:{}", eventGame.getEventId());
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_NOT_EXIST);
        }

        MarketingEvent marketingEvent = marketingEventOptional.get();
        TournamentConfigCacheDto tournamentConfigCacheDto = tournamentConfigCacheDtoMapper.convert(tournamentConfig);
        TournamentMarketingEventCacheDto tournamentMarketingEventCacheDto = tournamentMarketingEventCacheDtoMapper.convert(marketingEvent);
        if (StringUtils.isNotBlank(marketingEvent.getBackgroundImageIobsKey())) {
            tournamentMarketingEventCacheDto.setBackgroundImageUrl(minioTemplate.getInternetUrl(marketingEvent.getBackgroundImageIobsKey()));
        }

        tournamentConfigCacheDto.setEvent(tournamentMarketingEventCacheDto);
        List<EventGameRewardItem> eventGameRewardItems = eventGameRewardItemDomainService.findByEventGameId(eventGame.getId(), EventGameRewardItem.class);
        List<TournamentRewardItemCacheDto> tournamentRewardItems = buildTournamentRewardItemDto(eventGameRewardItems);
        tournamentConfigCacheDto.setTournamentRewards(tournamentRewardItems);

        return tournamentConfigCacheDto;
    }


    private List<TournamentRewardItemCacheDto> buildTournamentRewardItemDto(List<EventGameRewardItem> eventGameRewardItems) {
        List<BigInteger> rewardRuleIds = eventGameRewardItems.stream().map(EventGameRewardItem::getRewardRuleId).filter(Objects::nonNull).collect(Collectors.toList());
        Map<BigInteger, MarketingRule> marketingRuleMap = marketingRuleDomainService.findAllById(rewardRuleIds, MarketingRule.class).stream().collect(
                Collectors.toMap(MarketingRule::getId, Function.identity(), (v1, v2) -> v1));
        List<TournamentRewardItemCacheDto> tournamentRewardItems = new ArrayList<>();
        for (EventGameRewardItem eventGameRewardItem : eventGameRewardItems) {
            TournamentRewardItemCacheDto tournamentRewardItemCacheDto = new TournamentRewardItemCacheDto();
            tournamentRewardItemCacheDto.setAttributes(eventGameRewardItem.getAttributes());
            MarketingRule marketingRule = marketingRuleMap.get(eventGameRewardItem.getRewardRuleId());
            TournamentRewardItemRuleCacheDto tournamentRewardItemRuleCacheDto = tournamentRewardItemRuleCacheDtoMapper.convert(marketingRule);
            tournamentRewardItemCacheDto.setRewardRule(tournamentRewardItemRuleCacheDto);
            tournamentRewardItemCacheDto.setEventGameRewardItemId(eventGameRewardItem.getId());
            tournamentRewardItemCacheDto.setQuantity(eventGameRewardItem.getQuantity());
            tournamentRewardItemCacheDto.setClaimType(eventGameRewardItem.getClaimType());
            tournamentRewardItems.add(tournamentRewardItemCacheDto);
        }
        return tournamentRewardItems;
    }


    public TournamentConfigVo queryTournamentConfigVo(BigInteger eventGameId) {
        TournamentConfigCacheDto tournamentConfigCacheDto = queryTournamentConfig(eventGameId);
        TournamentConfigVo tournamentConfigVo = tournamentConfigVoMapper.convert(tournamentConfigCacheDto);
        return tournamentConfigVo;
    }

    public void batchDeleteTournamentConfigCache(List<BigInteger> eventGameIds) {
        if(CollectionUtils.isEmpty(eventGameIds)){
            logger.info("deleteTournamentConfigCache, eventGameIds is empty");
            return;
        }

        var tournamentConfigCacheKeys = new ArrayList<String>();
        for (BigInteger eventGameId : eventGameIds) {
            var tournamentConfigKey = TournamentCacheKeyUtils.getTournamentConfigKey(eventGameId);
            tournamentConfigCacheKeys.add(tournamentConfigKey);
        }

        stringRedisTemplate.delete(tournamentConfigCacheKeys);
    }
}
