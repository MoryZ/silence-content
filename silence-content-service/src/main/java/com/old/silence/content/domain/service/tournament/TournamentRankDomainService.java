package com.old.silence.content.domain.service.tournament;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.api.TournamentPersonalRankQuery;
import com.old.silence.content.api.dto.CurrentGroupVO;
import com.old.silence.content.api.dto.RankItemVO;
import com.old.silence.content.api.dto.TournamentCurrentGroupVO;
import com.old.silence.content.api.dto.TournamentGlobalRankQuery;
import com.old.silence.content.api.dto.TournamentRankListVO;
import com.old.silence.content.api.tournament.tournament.dto.TournamentGroupInfoCacheDTO;
import com.old.silence.content.api.tournament.tournament.dto.TournamentRankRecordCacheDto;
import com.old.silence.content.api.tournament.tournament.dto.TournamentRankingDto;
import com.old.silence.content.api.tournament.tournament.dto.TournamentRobotCacheDTO;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentRankingType;
import com.old.silence.content.util.TournamentCacheKeyUtils;
import com.old.silence.json.JacksonMapper;
import com.old.silence.content.domain.common.BizErrorCode;
import com.old.silence.content.domain.common.MarketingBizException;
import com.old.silence.content.domain.common.TournamentCacheDurationConstant;
import com.old.silence.content.domain.model.TournamentRanking;
import com.old.silence.content.domain.model.TournamentScoreRecord;
import com.old.silence.content.domain.repository.tournament.TournamentRankingRepository;
import com.old.silence.content.domain.repository.tournament.TournamentScoreRecordRepository;
import com.old.silence.content.infrastructure.cache.RedisClient;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author moryzang
 */
@Service
public class TournamentRankDomainService {
    private static final Logger logger = LoggerFactory.getLogger(TournamentRankDomainService.class);

    private RedisClient redisClient;

    private TournamentRankingRepository tournamentRankingRepository;

    private final TournamentScoreRecordRepository tournamentScoreRecordRepository;

    private final JacksonMapper jacksonMapper;

    private final TournamentConfigCacheDomainService tournamentConfigCacheDomainService;

    private final TournamentGroupDomainService tournamentGroupDomainService;

    private final TournamentRobotDomainService tournamentRobotDomainService;

    private final TournamentParticipationRecordService tournamentParticipationRecordService;

    public TournamentRankDomainService(RedisClient redisClient, JacksonMapper jacksonMapper,
            TournamentConfigCacheDomainService tournamentConfigCacheDomainService,
            TournamentRankingRepository tournamentRankingRepository,
            TournamentScoreRecordRepository tournamentScoreRecordRepository,
            TournamentGroupDomainService tournamentGroupDomainService,
            TournamentRobotDomainService tournamentRobotDomainService,
            TournamentParticipationRecordService tournamentParticipationRecordService) {
        this.redisClient = redisClient;
        this.jacksonMapper = jacksonMapper;
        this.tournamentConfigCacheDomainService = tournamentConfigCacheDomainService;
        this.tournamentRankingRepository = tournamentRankingRepository;
        this.tournamentScoreRecordRepository = tournamentScoreRecordRepository;
        this.tournamentGroupDomainService = tournamentGroupDomainService;
        this.tournamentRobotDomainService = tournamentRobotDomainService;
        this.tournamentParticipationRecordService = tournamentParticipationRecordService;
    }

    /**
     * 查询排行榜详情（入口方法）
     */
    public TournamentRankListVO getRankDetail(TournamentGlobalRankQuery query) {
        return getRankDetailWithCache(query);
    }

    /**
     * 查询排行榜详情（带缓存）
     */
    public TournamentRankListVO getRankDetailWithCache(TournamentGlobalRankQuery query) {
        String cacheKey;
        Duration duration;
        if (query.getRankType() == null || query.getEventGameId() == null) {
            throw new MarketingBizException(BizErrorCode.INVALID_PARAMS, "rankType和eventGameId不能为空");
        }
        // 1. 构建缓存 key 和过期时间
        if (TournamentRankingType.GROUP.equals(query.getRankType())) {
            if (query.getGroupId() == null) {
                throw new MarketingBizException(BizErrorCode.INVALID_PARAMS, "rankType为小组时，groupId不能为空");
            }

            cacheKey = TournamentCacheKeyUtils.getTournamentGroupRankKey(query.getEventGameId(), query.getGroupId());
            duration = Duration.ofDays(TournamentCacheDurationConstant.TOURNAMENT_RANK_GROUP_DURATION);
        } else {
            cacheKey = TournamentCacheKeyUtils.getTournamentGlobalRankKey(query.getEventGameId());
            duration = Duration.ofDays(TournamentCacheDurationConstant.TOURNAMENT_RANK_NATIONAL_DURATION);
        }
        // 2. 使用 RedisClient 缓存模板：先查缓存，未命中则查库并写入
        return redisClient.get(cacheKey, null, TournamentRankListVO.class, id -> buildRankingFromDb(query), duration);
    }

    /**
     * 从数据库构建排行榜数据
     */
    private TournamentRankListVO buildRankingFromDb(TournamentGlobalRankQuery query) {
        List<TournamentRanking> rankings = tournamentRankingRepository.findByEventGameIdAndRankingTypeAndGroupId(
                query.getEventGameId(), query.getRankType(),
                TournamentRankingType.GROUP.equals(query.getRankType()) ? query.getGroupId() : null);

        // 排序：按 rankNo 升序
        rankings.sort(Comparator.comparing(TournamentRanking::getRankNo));

        TournamentRankListVO vo = new TournamentRankListVO();

        // 设置 currentGroup（仅小组）
        if (TournamentRankingType.GROUP.equals(query.getRankType())) {
            TournamentGroupInfoCacheDTO groupInfo = tournamentGroupDomainService.getGroupInfoWithCache(
                    query.getEventGameId(), query.getGroupId());
            CurrentGroupVO currentGroupVO = new CurrentGroupVO();
            currentGroupVO.setGroupId(query.getGroupId());
            currentGroupVO.setTierName(groupInfo.getTierName());
            currentGroupVO.setTierOrder(groupInfo.getTierOrder());
            currentGroupVO.setTierIcon(groupInfo.getTierIcon());
            vo.setCurrentGroupVO(currentGroupVO);
        }
        // 转换为 RankItemVO 列表
        List<RankItemVO> rankItems = rankings.stream().map(ranking -> {
            RankItemVO item = new RankItemVO();
            item.setRank(ranking.getRankNo());
            item.setParticipantId(ranking.getParticipantId());
            item.setParticipantType(ranking.getParticipantType().getDescription());
            if (TournamentParticipantType.ROBOT.getValue().equals(ranking.getParticipantType().getValue())) {
                TournamentRobotCacheDTO robotInfo = tournamentRobotDomainService.getRobotInfoWithCache(
                        query.getEventGameId(), ranking.getParticipantId());
                item.setAvatarUrl(robotInfo.getAvatarUrl());
                item.setName(robotInfo.getNickname());
            }
            item.setTotalScore(ranking.getScore());
            return item;
        }).collect(Collectors.toList());
        vo.setRankItemVOList(rankItems);
        return vo;
    }

    /**
     * 计算用户当前时间 所处阶段的得分
     */
    public TournamentCurrentGroupVO getPersonalRank(TournamentPersonalRankQuery query) {
        // 1. 校验入参是否合规
        if (Objects.isNull(query)) {
            throw new MarketingBizException(BizErrorCode.INVALID_PARAMS, "请求参数不能为空");
        }
        if (Objects.isNull(query.getEventGameId()) || Objects.isNull(query.getPartyId()) || Objects.isNull(
                query.getRankType())) {
            throw new MarketingBizException(BizErrorCode.INVALID_PARAMS, "eventGameId、partyId、rankType均不能为空");
        }
        BigInteger eventGameId = query.getEventGameId();
        BigInteger participantId = query.getPartyId();
        // 2. 判断用户是否报名
        boolean isRegistered = tournamentParticipationRecordService.existTournamentParticipationRecord(eventGameId,
                participantId, TournamentParticipantType.PARTY);
        if (!isRegistered) {
            logger.warn("用户未报名, eventGameId={}, partyId={}", eventGameId, participantId);
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_REGISTRATION_NOT_FOUND);
        }

        // 3. 构建缓存 key 和 duration
        String cacheKey;
        Duration duration;
        Object loaderParam;
        if (TournamentRankingType.GROUP.equals(query.getRankType())) {
            Instant now = Instant.now();
            int currentCycle = TournamentStageCalculationService.calculateCurrentCycle(
                    tournamentConfigCacheDomainService.queryTournamentConfig(eventGameId), now);
            logger.info("当前时间：{}, 当前周期：{}", now, currentCycle);
            cacheKey = TournamentCacheKeyUtils.getTournamentChallengeGroupScoreKey(eventGameId, participantId,
                    currentCycle);
            duration = Duration.ofDays(TournamentCacheDurationConstant.TOURNAMENT_CHALLENGE_SCORE_DURATION);
            loaderParam = currentCycle;
        } else {
            cacheKey = TournamentCacheKeyUtils.getTournamentChallengeNationalScoreKey(eventGameId, participantId);
            duration = Duration.ofDays(TournamentCacheDurationConstant.TOURNAMENT_RANK_NATIONAL_DURATION);
            loaderParam = null;
        }
        String scoreStr = redisClient.get(cacheKey, participantId, String.class,
                id -> loadPersonalRankFromDb(query, loaderParam), duration);

        // 5. 构造 VO 并返回（即使 score 为 null 也返回空 VO）
        TournamentCurrentGroupVO vo = new TournamentCurrentGroupVO();
        vo.setParticipantId(participantId);
        vo.setParticipantType(TournamentParticipantType.PARTY); // 假设你能确定类型
        vo.setScore(scoreStr); // 可能为 null
        return vo;
    }

    private String loadPersonalRankFromDb(TournamentPersonalRankQuery query, Object loaderParam) {
        BigInteger eventId = query.getEventGameId();
        BigInteger participantId = query.getPartyId();
        TournamentRankingType rankType = query.getRankType();

        if (TournamentRankingType.GROUP.equals(rankType)) {
            // 获取当前周期
            int currentCycle = (Integer) loaderParam;
            Optional<TournamentScoreRecord> optional =
                    tournamentScoreRecordRepository.findByEventGameIdAndParticipantIdAndCycleNumber(eventId,
                            participantId, currentCycle);

            return optional.map(record -> record.getScore()).filter(Objects::nonNull).map(BigDecimal::toPlainString)
                    .orElse(null);

        } else if (TournamentRankingType.NATIONAL.equals(rankType)) {
            Optional<TournamentRanking> optional =
                    tournamentRankingRepository.findByEventGameIdAndParticipantIdAndRankingType(eventId, participantId,
                            rankType);

            return optional.map(TournamentRanking::getScore).filter(Objects::nonNull).map(BigDecimal::toPlainString)
                    .orElse(null);
        }
        return null;
    }

    public TournamentRankRecordCacheDto findGlobalRankParticipationRecordWithCache(BigInteger eventGameId, BigInteger participantId,
                                                                                   TournamentParticipantType participantType) {

        var rankingType = TournamentRankingType.NATIONAL;
        var rankParticipationRecordKey = TournamentCacheKeyUtils.getTournamentRankParticipationRecordKey(eventGameId,
                participantId, participantType, rankingType);
        TournamentRankRecordCacheDto rankRecordCache;
        var rankRecordCacheStr = redisClient.getStrValue(rankParticipationRecordKey);
        if (StringUtils.isBlank(rankRecordCacheStr)) {
            var rankRecordOptional = tournamentRankingRepository.findByEventGameIdAndParticipantIdAndParticipantTypeAndRankingType(
                    eventGameId, participantId, participantType, rankingType, TournamentRanking.class);
            var tournamentRanking = rankRecordOptional.orElse((null));
            rankRecordCache = tournamentRankParticipationRecordWithCache(rankParticipationRecordKey, tournamentRanking);
            return rankRecordCache;
        }

        rankRecordCache = jacksonMapper.fromJson(rankRecordCacheStr,
                TournamentRankRecordCacheDto.class);
        return rankRecordCache;
    }

    private TournamentRankRecordCacheDto tournamentRankParticipationRecordWithCache(String cacheKey,TournamentRanking tournamentRanking) {

        var participantGroupCacheDto = buildTournamentRankRecordCacheDto(tournamentRanking);
        var participantGroupCacheJson = jacksonMapper.toJson(participantGroupCacheDto);
        redisClient.set(cacheKey, participantGroupCacheJson,
                TournamentCacheDurationConstant.TOURNAMENT_RANK_PARTICIPANT_RECORD_DURATION, TimeUnit.MINUTES);

        return participantGroupCacheDto;
    }

    private TournamentRankRecordCacheDto buildTournamentRankRecordCacheDto(TournamentRanking tournamentRanking){
        var rankRecordCacheDto = new TournamentRankRecordCacheDto();
        rankRecordCacheDto.setScore(tournamentRanking.getScore());
        rankRecordCacheDto.setRankNo(tournamentRanking.getRankNo());
        return rankRecordCacheDto;
    }

    public void batchAddRankRecord(List<TournamentRankingDto> tournamentRankingList) {
        var tournamentRankings = new ArrayList<TournamentRanking>();
        for (var tournamentRankingDto : tournamentRankingList) {
            var tournamentRanking = new TournamentRanking();
            tournamentRanking.setEventGameId(tournamentRankingDto.getEventGameId());
            tournamentRanking.setParticipantId(tournamentRankingDto.getParticipantId());
            tournamentRanking.setParticipantType(tournamentRankingDto.getParticipantType());
            tournamentRanking.setScore(tournamentRankingDto.getScore());
            tournamentRanking.setRankingType(TournamentRankingType.NATIONAL);
            tournamentRanking.setRankNo(tournamentRankingDto.getRankNo());
            tournamentRankings.add(tournamentRanking);
        }
        tournamentRankingRepository.bulkCreate(tournamentRankings);
    }
}

