package com.old.silence.content.domain.service.tournament;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.old.silence.content.api.assembler.EventGameRewardItemMapper;
import com.old.silence.content.api.assembler.MarketingEventMapper;
import com.old.silence.content.api.assembler.tournament.TournamentConfigMapper;
import com.old.silence.content.api.tournament.tournament.dto.TournamentConfigCacheDto;
import com.old.silence.content.api.tournament.tournament.dto.TournamentConfigDomainCommand;
import com.old.silence.content.api.tournament.tournament.dto.TournamentParticipantGroupCacheDto;
import com.old.silence.content.api.tournament.tournament.dto.TournamentRankRecordCacheDto;
import com.old.silence.content.api.tournament.tournament.dto.TournamentUserParticipationInfoCommand;
import com.old.silence.content.api.tournament.tournament.dto.support.CycleStageConfig;
import com.old.silence.content.api.tournament.tournament.vo.TournamentParticipantGroupVo;
import com.old.silence.content.api.tournament.tournament.vo.TournamentTierVo;
import com.old.silence.content.api.tournament.tournament.vo.TournamentUserParticipationInfoVo;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentStageType;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.json.JacksonMapper;
import com.old.silence.content.domain.enums.EventGameType;
import com.old.silence.content.domain.enums.MarketingEventStatus;
import com.old.silence.content.domain.model.EventGame;
import com.old.silence.content.domain.repository.EventGameRepository;
import com.old.silence.content.domain.repository.EventGameRewardItemRepository;
import com.old.silence.content.domain.repository.MarketingEventRepository;
import com.old.silence.content.domain.repository.tournament.TournamentConfigRepository;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentConfigDomainService {

    private static final Logger logger = LoggerFactory.getLogger(TournamentConfigDomainService.class);
    private static final String TOURNAMENT_VERSION = "V1";

    private final TournamentConfigMapper tournamentConfigMapper;
    private final MarketingEventMapper marketingEventMapper;
    private final EventGameRewardItemMapper eventGameRewardItemMapper;

    private final MarketingEventRepository marketingEventRepository;
    private final EventGameRepository eventGameRepository;
    private final EventGameRewardItemRepository eventGameRewardItemRepository;
    private final TournamentConfigRepository tournamentConfigRepository;

    private final TournamentConfigCacheDomainService tournamentConfigCacheDomainService;
    private final TournamentParticipationRecordService tournamentParticipationRecordService;
    private final TournamentGroupDomainService tournamentGroupDomainService;
    private final TournamentChallengeDomainService tournamentChallengeDomainService;
    private final TournamentRankDomainService tournamentRankDomainService;

    public TournamentConfigDomainService(TournamentConfigMapper tournamentConfigMapper,
                                         MarketingEventMapper marketingEventMapper,
                                         EventGameRewardItemMapper eventGameRewardItemMapper,
                                         MarketingEventRepository marketingEventRepository,
                                         EventGameRepository eventGameRepository,
                                         EventGameRewardItemRepository eventGameRewardItemRepository,
                                         TournamentConfigRepository tournamentConfigRepository,
                                         TournamentConfigCacheDomainService tournamentConfigCacheDomainService,
                                         TournamentParticipationRecordService tournamentParticipationRecordService,
                                         TournamentGroupDomainService tournamentGroupDomainService,
                                         TournamentChallengeDomainService tournamentChallengeDomainService,
                                         TournamentRankDomainService tournamentRankDomainService) {
        this.tournamentConfigMapper = tournamentConfigMapper;
        this.marketingEventMapper = marketingEventMapper;
        this.eventGameRewardItemMapper = eventGameRewardItemMapper;
        this.marketingEventRepository = marketingEventRepository;
        this.eventGameRepository = eventGameRepository;
        this.eventGameRewardItemRepository = eventGameRewardItemRepository;
        this.tournamentConfigRepository = tournamentConfigRepository;
        this.tournamentConfigCacheDomainService = tournamentConfigCacheDomainService;
        this.tournamentParticipationRecordService = tournamentParticipationRecordService;
        this.tournamentGroupDomainService = tournamentGroupDomainService;
        this.tournamentChallengeDomainService = tournamentChallengeDomainService;
        this.tournamentRankDomainService = tournamentRankDomainService;
    }


    public <T> Optional<T> findByEventGameId(BigInteger eventGameId, Class<T> projectionType) {
        return tournamentConfigRepository.findByEventGameId(eventGameId, projectionType);
    }

    public <T> Page<T> query(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return tournamentConfigRepository.findByCriteria(criteria, pageable, projectionType);
    }


    @Transactional
    public BigInteger create(TournamentConfigDomainCommand tournamentConfigDomainCommand) {

        var marketingEvent = marketingEventMapper.convert(tournamentConfigDomainCommand.getMarketingEvent());
        marketingEvent.setStatus(MarketingEventStatus.DRAFT);
        marketingEventRepository.create(marketingEvent); // NOSONAR

        var eventGame = new EventGame();
        eventGame.setEventId(marketingEvent.getId());
        eventGame.setName(marketingEvent.getName());
        eventGame.setType(EventGameType.PK_TOURNAMENT);
        eventGame.setEffectiveAt(marketingEvent.getStartTime());
        eventGame.setExpiresAt(marketingEvent.getEndTime());
        eventGame.setEventGameRewardItems(CollectionUtils.transformToList(tournamentConfigDomainCommand.getEventGameRewardItems(), eventGameRewardItemMapper::convert));
        eventGameRepository.createEventGameWithReward(eventGame);

        var tournamentConfig = tournamentConfigMapper.convert(tournamentConfigDomainCommand);
        tournamentConfig.setEventGameId(eventGame.getId());
        tournamentConfig.setVersion(TOURNAMENT_VERSION);
        tournamentConfigRepository.create(tournamentConfig);
        return tournamentConfig.getId();
    }

    @Transactional
    public int update(BigInteger id, TournamentConfigDomainCommand tournamentConfigDomainCommand) {
        var marketingEvent = marketingEventMapper.convert(tournamentConfigDomainCommand.getMarketingEvent());
        marketingEvent.setStatus(MarketingEventStatus.DRAFT);
        marketingEventRepository.update(marketingEvent);

        var eventGameRewardItems = CollectionUtils.transformToList(tournamentConfigDomainCommand.getEventGameRewardItems(), eventGameRewardItemMapper::convert);
        eventGameRewardItemRepository.bulkReplace(tournamentConfigDomainCommand.getEventGameId(), eventGameRewardItems);

        var tournamentConfig = tournamentConfigMapper.convert(tournamentConfigDomainCommand);
        tournamentConfig.setId(id);
        int row = tournamentConfigRepository.updateNonNull(tournamentConfig);
        tournamentConfigCacheDomainService.batchDeleteTournamentConfigCache(List.of(tournamentConfigDomainCommand.getEventGameId()));
        return row;
    }


    public TournamentUserParticipationInfoVo queryTournamentUserParticipationInfo(TournamentUserParticipationInfoCommand command){
        logger.info("queryTournamentUserParticipationInfo command:{}", JacksonMapper.getSharedInstance().toJson(command));
        var participantId = command.getPartyId();
        var participantType = TournamentParticipantType.PARTY;
        var eventGameId = command.getEventGameId();
        var participationInfoVo = new TournamentUserParticipationInfoVo();

        //赛事信息
        var tournamentConfig = tournamentConfigCacheDomainService.queryTournamentConfig(eventGameId);

        //报名信息
        var tournamentParticipationRecord = tournamentParticipationRecordService.findTournamentParticipationWithCache(
                eventGameId, participantId, participantType);
        participationInfoVo.setRegisteredStatus(false);
        if(tournamentParticipationRecord != null){
            participationInfoVo.setRegisteredStatus(tournamentParticipationRecord.getId() != null);
            participationInfoVo.setRegistrationTime(tournamentParticipationRecord.getRegistrationTime());
        }

        //未报名不返回下面信息
        if(!participationInfoVo.getRegisteredStatus()){
            logger.info("tournamentTime not register");
            return participationInfoVo;
        }

        //计算周期号
        var now = Instant.now();
        boolean tournamentTimeStart = TournamentStageCalculationService.isTournamentTimeStart(now, tournamentConfig.getTournamentStartTime());
        if(!tournamentTimeStart){
            //赛事时间未到
            logger.info("tournamentTime not start");
            return participationInfoVo;
        }

        boolean allCycleEnd = TournamentStageCalculationService.isAllCycleEnd(now, tournamentConfig.getTournamentEndTime());
        logger.info("allCycleEnd:{}",allCycleEnd);
        if(allCycleEnd){
            //最终段位，所有周期结束后
            var participationGlobalRank = tournamentRankDomainService.findGlobalRankParticipationRecordWithCache(eventGameId, participantId, participantType);
            var finalTier = getFinalTier(tournamentConfig, participationGlobalRank);
            participationInfoVo.setFinalTier(finalTier);
        }else {
            var cycleNumber = TournamentStageCalculationService.calculateCurrentCycle(tournamentConfig, now);
            //用户当前周期分组信息
            var stageType = TournamentStageType.CYCLE;
            var participantGroup = tournamentGroupDomainService.findParticipantGroupWithCache(
                    eventGameId, participantId, participantType, stageType, cycleNumber);
            participationInfoVo.setCurrentGroup(buildTournamentGroupVo(participantGroup));

            //统计用户场次记录
            var tournamentActive = TournamentStageCalculationService.isTournamentActive(tournamentConfig, now);
            logger.info("cycleNumber:{},tournamentActive:{}",cycleNumber, tournamentActive);
            if(tournamentActive){
                Integer segmentNumber = 1;
                Integer stageNumber = 1;
                //统计当前场次挑战次数
                Integer participantStageCount = tournamentChallengeDomainService.getChallengeCountWithCache(
                        eventGameId,participantId,cycleNumber,segmentNumber,stageNumber);
                participationInfoVo.setSessionChallengeCount(participantStageCount);
                boolean hasSessionChallenge = participantStageCount != null && participantStageCount > 0;
                participationInfoVo.setSessionChallengeStatus(hasSessionChallenge ? 1 : 0);
            }
        }

        return participationInfoVo;
    }

    private TournamentParticipantGroupVo buildTournamentGroupVo(TournamentParticipantGroupCacheDto participantGroup) {
        if(participantGroup == null){
            return null;
        }
        var tournamentParticipantGroupVo = new TournamentParticipantGroupVo();
        tournamentParticipantGroupVo.setGroupId(participantGroup.getGroupId());
        tournamentParticipantGroupVo.setTierName(participantGroup.getTierName());
        tournamentParticipantGroupVo.setTierIcon(participantGroup.getTierIcon());
        return tournamentParticipantGroupVo;
    }

    private TournamentTierVo getFinalTier(TournamentConfigCacheDto tournamentConfig, TournamentRankRecordCacheDto rankRecord){
        if(rankRecord == null || rankRecord.getRankNo() == null){
            logger.info("rankRecord is null");
            return null;
        }

        var tournamentTierVo = new TournamentTierVo();
        var cycleStageConfig = tournamentConfig.converterAttributes(CycleStageConfig.class);
        var rankNo = rankRecord.getRankNo();
        var tierDefinitions = cycleStageConfig.getTournamentTierDefinitions();
        if(CollectionUtils.isEmpty(tierDefinitions)){
            logger.info("tournamentTierDefinitions is empty");
            return null;
        }

        for (var tournamentTier : tierDefinitions) {
            var tierMapping = tournamentTier.getTierMapping();
            var rankStart = tierMapping.getRankStart();
            var rankEnd = tierMapping.getRankEnd();
            if(rankNo >= rankStart && rankNo <= rankEnd){
                tournamentTierVo.setTierName(tournamentTier.getTierName());
                tournamentTierVo.setTierIcon(tournamentTier.getTierImageUrl());
                return tournamentTierVo;
            }
        }

        return null;
    }
}
