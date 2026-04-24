package com.old.silence.content.domain.service.tournament;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.old.silence.content.api.dto.CompleteChallengeCommand;
import com.old.silence.content.api.dto.ExerciseDetailVO;
import com.old.silence.content.api.dto.ExerciseSubmitItem;
import com.old.silence.content.api.dto.StartChallengeCommand;
import com.old.silence.content.api.dto.TournamentActiveChallengeCacheDto;
import com.old.silence.content.api.tournament.tournament.dto.TournamentConfigCacheDto;
import com.old.silence.content.api.tournament.tournament.dto.TournamentParticipantGroupCacheDto;
import com.old.silence.content.api.tournament.tournament.dto.support.CycleStageConfig;
import com.old.silence.content.api.tournament.tournament.dto.support.ExerciseScoreCoefficient;
import com.old.silence.content.api.vo.TournamentFinishVO;
import com.old.silence.content.api.vo.TournamentStartVO;
import com.old.silence.content.domain.common.BizErrorCode;
import com.old.silence.content.domain.common.MarketingBizException;
import com.old.silence.content.domain.common.TournamentCacheDurationConstant;
import com.old.silence.content.domain.enums.MarketingEventStatus;
import com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentStageType;
import com.old.silence.content.domain.service.view.TournamentChallengeRecordOnlyFinalScoreView;
import com.old.silence.content.util.TournamentCacheKeyUtils;
import com.old.silence.content.util.TournamentLockKeyUtils;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.json.JacksonMapper;

import com.old.silence.content.domain.model.TournamentChallengeRecord;
import com.old.silence.content.domain.repository.tournament.TournamentChallengeRepository;
import com.old.silence.content.infrastructure.cache.RedisClient;
import com.old.silence.content.infrastructure.lock.RedissonLock;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.old.silence.content.domain.common.TournamentCacheDurationConstant.TOURNAMENT_ACTIVE_CHALLENGE_DURATION;

/**
 * @author moryzang
 */
@Service
public class TournamentChallengeDomainService {
    private static final Logger logger = LoggerFactory.getLogger(TournamentChallengeDomainService.class);

    private final RedisClient redisClient;

    private final TournamentParticipationRecordService tournamentParticipationRecordService;

    private final JacksonMapper jacksonMapper;

    private final TournamentChallengeRepository tournamentChallengeRepository;

    private final TournamentConfigCacheDomainService tournamentConfigCacheDomainService;

    private final RedissonLock redissonLock;

    private final TournamentGroupDomainService tournamentGroupDomainService;

    public TournamentChallengeDomainService(RedisClient redisClient,
            TournamentParticipationRecordService tournamentParticipationRecordService, JacksonMapper jacksonMapper,
            TournamentChallengeRepository tournamentChallengeRepository,
            TournamentConfigCacheDomainService tournamentConfigCacheDomainService, RedissonLock redissonLock,
            TournamentGroupDomainService tournamentGroupDomainService) {
        this.redisClient = redisClient;
        this.tournamentParticipationRecordService = tournamentParticipationRecordService;
        this.jacksonMapper = jacksonMapper;
        this.tournamentChallengeRepository = tournamentChallengeRepository;
        this.tournamentConfigCacheDomainService = tournamentConfigCacheDomainService;
        this.redissonLock = redissonLock;
        this.tournamentGroupDomainService = tournamentGroupDomainService;
    }

    // 默认运动项（备用）
    private static final List<ExerciseScoreCoefficient> DEFAULT_EXERCISE_ITEMS = List.of(
            new ExerciseScoreCoefficient() {{

            }});


    public TournamentStartVO startChallenge(StartChallengeCommand command) {
        BigInteger eventGameId = command.getEventGameId();
        BigInteger partyId = command.getPartyId(); // 直接使用 partyId
        BigInteger memberId = command.getMemberId();

        if (eventGameId == null || partyId == null || memberId == null) {
            throw new MarketingBizException(BizErrorCode.REQUEST_NOT_UP_TO_STANDARD, "eventGameId 或 partyId 不能为空");
        }

        Instant now = Instant.now();
        logger.info("开始挑战流程, eventGameId={}, partyId={}, time={}", eventGameId, partyId, now);

        // 1.校验报名情况 查缓存（registration）
        boolean isRegistered = tournamentParticipationRecordService.existTournamentParticipationRecord(eventGameId,
                partyId, TournamentParticipantType.PARTY);
        if (!isRegistered) {
            logger.warn("用户未报名, eventGameId={}, partyId={}", eventGameId, partyId);
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_REGISTRATION_NOT_FOUND);
        }
        // 2. 获取活动缓存配置
        TournamentConfigCacheDto config = tournamentConfigCacheDomainService.queryTournamentConfig(eventGameId);
        // 3. 校验赛事是否处于有效周期内（报名或比赛中）
        if (!TournamentStageCalculationService.isTournamentActive(config, now) || !config.getEvent().getStatus().equals(
                MarketingEventStatus.PUBLISHED)) {
            logger.warn("赛事不在有效周期内, eventGameId={}, now={}", eventGameId, now);
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_INVALID_TIME_RANGE);
        }

        // 4.构造开始挑战缓存 无论缓存是否有进行中比赛，用户上报开始比赛后均重新生成挑战
        String challengeId = UUID.randomUUID().toString();
        Instant startTime = now.truncatedTo(ChronoUnit.MICROS); // 去掉纳秒，避免序列化问题
        String activeChallengeKey = TournamentCacheKeyUtils.getTournamentActiveChallengeKey(eventGameId, partyId);

        // 5. 提取本次挑战的运动项（3个）
        List<ExerciseScoreCoefficient> exerciseItems = extractExerciseItems(config);
        if (exerciseItems.isEmpty()) {
            logger.warn("未能提取到任何运动项, eventGameId={}", eventGameId);
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_EXERCISE_ITEMS_EMPTY);
        }
        // 6. 生成挑战记录缓存active_challenge
        TournamentActiveChallengeCacheDto newActive = new TournamentActiveChallengeCacheDto(challengeId, startTime,
                exerciseItems);
        String newActiveStr = jacksonMapper.toJson(newActive);
        // 缓存时间：挑战超时时间 默认三十分钟
        long timeoutSeconds = (config.getChallengeTimeoutMinutes() != null ? config.getChallengeTimeoutMinutes()
                : TOURNAMENT_ACTIVE_CHALLENGE_DURATION) * 60L;
        redisClient.set(activeChallengeKey, newActiveStr, timeoutSeconds, TimeUnit.SECONDS);
        logger.info("创建新挑战成功, challengeId={}, eventGameId={}, partyId={}, startTime={}", challengeId,
                eventGameId, partyId, startTime);

        // 7. 构造并返回 VO
        return buildTournamentStartVO(challengeId, exerciseItems);
    }

    public TournamentFinishVO completeChallenge(CompleteChallengeCommand command) {
        String challengeId = command.getChallengeId();
        List<ExerciseSubmitItem> exerciseDetails = command.getExerciseDetails();
        BigInteger eventGameId = command.getEventGameId();
        BigInteger partyId = command.getPartyId();
        BigInteger memberId = command.getMemberId();

        // 参数校验
        if (StringUtils.isEmpty(challengeId)) {
            logger.warn("challengeId 不能为空, command: {}", command);
            throw new MarketingBizException(BizErrorCode.REQUEST_NOT_UP_TO_STANDARD);
        }
        if (eventGameId == null || partyId == null || memberId == null) {
            logger.warn("eventGameId 或 partyId 不能为空, command: {}", command);
            throw new MarketingBizException(BizErrorCode.REQUEST_NOT_UP_TO_STANDARD);
        }
        if (CollectionUtils.isEmpty(exerciseDetails)) {
            logger.warn("exerciseDetails 不能为空, command: {}", command);
            throw new MarketingBizException(BizErrorCode.REQUEST_NOT_UP_TO_STANDARD);
        }

        Instant endTime = Instant.now();
        logger.info("开始完成挑战流程, eventGameId={}, challengeId={}, submitSize={}", eventGameId, challengeId,
                exerciseDetails.size());

        // 1. 构造缓存 key，获取进行中的挑战
        String activeChallengeKey = TournamentCacheKeyUtils.getTournamentActiveChallengeKey(eventGameId, partyId);
        String activeStr = redisClient.getStrValue(activeChallengeKey);
        // 进行中挑战记录不存在，超时均查询不到对应value
        if (activeStr == null) {
            logger.warn("未找到进行中的挑战记录或者本次挑战超时, key={}", activeChallengeKey);
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_CHALLENGE_NOT_FOUND);
        }

        TournamentActiveChallengeCacheDto activeChallenge;
        try {
            activeChallenge = jacksonMapper.fromJson(activeStr, TournamentActiveChallengeCacheDto.class);
        } catch (Exception e) {
            logger.error("进行中挑战缓存反序列化失败, key={}", activeChallengeKey, e);
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_CACHE_PARSE_ERROR);
        }

        // 2. 校验challengeId是否有效
        if (!challengeId.equals(activeChallenge.getChallengeId())) {
            logger.warn("challengeId 不匹配, activeChallenge={}, challengeId={}", activeChallenge, challengeId);
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_CHALLENGE_NOT_FOUND);
        }

        Instant startTime = activeChallenge.getStartTime();

        // 3. 获取赛事配置
        TournamentConfigCacheDto config = tournamentConfigCacheDomainService.queryTournamentConfig(eventGameId);
        // 4. 计算当前周期号 以开始时间为准来计算
        int currentCycle = TournamentStageCalculationService.calculateCurrentCycle(config, startTime);

        // 5. 校验当前场次是否在有效周期内
        // 完成挑战时 不需要显式校验挑战结束时间是否超过“开始挑战时间/场次结束时间 + buffer”  buffer过期 就代表比赛超时或者失效
        if (!TournamentStageCalculationService.isTournamentActive(config, startTime)) {
            logger.warn("赛事不在有效周期内, eventGameId={}, startTime={}", eventGameId, startTime);
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_INVALID_TIME_RANGE);
        }
        // 6. 校验任务项数量
        int completedCount = validateAndCountSubmittedItems(command.getExerciseDetails(),
                activeChallenge.getExerciseItems());
        logger.info("用户完成合法任务项数量: {}/{}", completedCount, activeChallenge.getExerciseItems().size());

        // 7. 计算得分
        BigDecimal basicScore = TournamentChallengeScoreDomainService.calculateBasicScore(exerciseDetails,
                activeChallenge.getExerciseItems());
        BigDecimal finalScore = basicScore;

        // 8. 生成挑战记录
        TournamentChallengeRecord record = buildTournamentChallengeRecord(command, config, activeChallenge,
                currentCycle, startTime, endTime, basicScore, finalScore);
        tournamentChallengeRepository.create(record);

        // 9. 清除挑战状态缓存
        redisClient.delete(activeChallengeKey);
        // 定义锁key
        String countlockKey = TournamentLockKeyUtils.getTournamentChallengeCountLockKey(eventGameId, partyId,
                TournamentParticipantType.PARTY, record.getCycleNumber(), record.getSegmentNumber(),
                record.getStageNumber());
        boolean result = false;
        try {
            result = redissonLock.tryLock(countlockKey, TimeUnit.SECONDS,
                    TournamentCacheDurationConstant.TOURNAMENT_LOCK_TIMEOUT);
            if (!result) {
                logger.info("competitionComplete in process");
                throw new MarketingBizException(BizErrorCode.TOURNAMENT_REGISTRATION_OPERATE_FREQUENTLY);
            }

            //  更新挑战次数缓存
            int count = getChallengeCountWithCache(eventGameId, partyId, record.getCycleNumber(),
                    record.getSegmentNumber(), record.getStageNumber());
            String challengeCountKey = TournamentCacheKeyUtils.getTournamentChallengeCountKey(eventGameId, partyId,
                    TournamentParticipantType.PARTY, record.getCycleNumber(), record.getSegmentNumber(),
                    record.getStageNumber());
            redisClient.set(challengeCountKey, String.valueOf(count + 1), 1l, TimeUnit.DAYS);
        } finally {
            if (result) {
                redissonLock.unlock(countlockKey);
            }
        }
        // 10. 返回结果
        TournamentFinishVO vo = new TournamentFinishVO();
        vo.setChallengeId(challengeId);
        vo.setBasicScore(basicScore);
        vo.setFinalScore(finalScore);

        logger.info("挑战完成并保存成功, challengeId={}, basicScore={}, finalScore={}", challengeId, basicScore,
                finalScore);

        return vo;

    }

    /**
     * 构造返回 VO
     */
    private TournamentStartVO buildTournamentStartVO(String challengeId, List<ExerciseScoreCoefficient> exerciseItems) {
        TournamentStartVO vo = new TournamentStartVO();
        vo.setChallengeId(challengeId);
        vo.setExercises(convertToExerciseDetailVO(exerciseItems));
        return vo;
    }

    /**
     * 从 attributes 中提取 exerciseItems，若无则返回默认
     */
    @SuppressWarnings("unchecked")
    private List<ExerciseScoreCoefficient> extractExerciseItems(TournamentConfigCacheDto config) {
        if (config == null || config.getAttributes() == null) {
            logger.warn("赛事配置attributes 为空, 使用默认运动项");
            return new ArrayList<>(DEFAULT_EXERCISE_ITEMS);
        }

        try {
            CycleStageConfig cycleStageConfig = config.converterAttributes(CycleStageConfig.class);
            List<ExerciseScoreCoefficient> coefficients = cycleStageConfig.getExerciseScoreCoefficients();
            if (coefficients == null || coefficients.isEmpty()) {
                logger.warn("exerciseScoreCoefficients 为空，使用默认运动项");
                return new ArrayList<>(DEFAULT_EXERCISE_ITEMS);
            }
            // 确保返回新列表，避免修改原数据
            List<ExerciseScoreCoefficient> selected = new ArrayList<>(coefficients);

            switch (cycleStageConfig.getPickMode()) {
                case RANDOM:
                    Collections.shuffle(selected);
                    break;
                case SEQUENCE:
                default:
                    // 已经是顺序，无需处理
                    break;
            }

            return selected;
        } catch (Exception e) {
            logger.error("解析 exerciseItems 失败，使用默认", e);
            return new ArrayList<>(DEFAULT_EXERCISE_ITEMS);
        }
    }

    /**
     * 将 Map 列表转换为 ExerciseDetailVO 列表
     */
    private List<ExerciseDetailVO> convertToExerciseDetailVO(List<ExerciseScoreCoefficient> coefficients) {
        return coefficients.stream().map(coef -> {
            ExerciseDetailVO vo = new ExerciseDetailVO();
            vo.setType(coef.getCode());           // code → type
            vo.setName(coef.getName());           // name → name
            vo.setCoefficient(coef.getScoreRate() != null ? coef.getScoreRate().doubleValue()
                    : 1.0);                           // scoreRate → coefficient，BigDecimal → Double
            vo.setDuration(coef.getDuration().toString()); // duration → duration（转为字符串）
            vo.setTimeUnit(coef.getUnit());       // unit → timeUnit
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 校验用户提交的任务项：
     * 1. 每一项的 type 必须在分配的任务中（允许重复，但不能超过分配数量）
     * 2. 提交总数必须 >= 2
     *
     * @param submittedItems 用户提交的项目列表
     * @param allowedItems 缓存中分配的 ExerciseDetailVO 列表
     * @return 合法提交的任务数量
     * @throws MarketingBizException 如果有非法项目
     */
    private int validateAndCountSubmittedItems(List<ExerciseSubmitItem> submittedItems,
            List<ExerciseScoreCoefficient> allowedItems) {
        if (submittedItems == null || submittedItems.isEmpty()) {
            logger.warn("用户未提交任何任务项");
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_TASK_ITEMS_INSUFFICIENT, "未提交任何任务");
        }

        // 提取缓存中允许所有比赛类型的 type
        List<String> availableTypes = allowedItems.stream().map(ExerciseScoreCoefficient::getCode).filter(
                Objects::nonNull).collect(Collectors.toList());

        if (availableTypes.isEmpty()) {
            logger.error("缓存中无允许的任务项");
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_EXERCISE_ITEMS_EMPTY);
        }
        if (submittedItems.size() < 3) {
            logger.warn("提交任务项数量不足3项, actual={}", submittedItems.size());
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_TASK_ITEMS_INSUFFICIENT, "至少需完成3项任务");
        }

        // 逐项校验并“消耗”可用 type
        for (ExerciseSubmitItem item : submittedItems) {
            String type = item.getType();

            if (type == null) {
                logger.warn("提交的任务项 type 为空");
                throw new MarketingBizException(BizErrorCode.REQUEST_NOT_UP_TO_STANDARD, "任务项 type 不能为空");
            }

            // 检查该 type 是否还有可用配额
            if (!availableTypes.remove(type)) {
                // remove 返回 false 表示没有这个 type 对应的挑战项
                logger.warn("提交了未分配或已耗尽的任务项: {}, 剩余允许: {}", type, availableTypes);
                throw new MarketingBizException(BizErrorCode.TOURNAMENT_EXERCISE_ITEM_NOT_ALLOWED);
            }
        }
        // 所有提交项都合法
        return submittedItems.size();

    }

    /**
     * 构造挑战记录
     */
    private TournamentChallengeRecord buildTournamentChallengeRecord(CompleteChallengeCommand command,
                                                                     TournamentConfigCacheDto config, TournamentActiveChallengeCacheDto activeChallenge, int cycleNumber,
                                                                     Instant startTime, Instant endTime, BigDecimal basicScore, BigDecimal finalScore) {

        TournamentChallengeRecord record = new TournamentChallengeRecord();
        record.setEventGameId(config.getEventGameId());
        record.setParticipantId(command.getPartyId());
        record.setParticipantType(TournamentParticipantType.PARTY);
        record.setContributorId(command.getPartyId());
        record.setCycleNumber(cycleNumber);
        record.setSegmentNumber(1);
        record.setStageNumber(1);
        TournamentParticipantGroupCacheDto tournamentGroup = tournamentGroupDomainService.findParticipantGroupWithCache(
                config.getEventGameId(), command.getPartyId(), TournamentParticipantType.PARTY,
                TournamentStageType.CYCLE, cycleNumber);
        record.setGroupId(tournamentGroup.getGroupId());
        record.setBaseScore(basicScore);
        record.setFinalScore(finalScore);
        record.setStatus(TournamentChallengeStatus.COMPLETED);
        record.setStartTime(startTime);
        record.setEndTime(endTime);
        List<ExerciseScoreCoefficient> exerciseItems = activeChallenge.getExerciseItems();
        Map<String, Object> detailsMap = new HashMap<>();
        detailsMap.put("exerciseScoreCoefficient", exerciseItems);
        record.setExerciseDetails(detailsMap);
        return record;
    }

    /**
     * 查询用户在指定赛事、周期、片号、场次中的挑战次数（带缓存）
     *
     * @param eventGameId 赛事ID
     * @param partyId 用户ID
     * @param cycleNumber 周期号
     * @param segmentNumber 片号
     * @param stageNumber 场次号
     * @return 挑战次数
     */
    public int getChallengeCountWithCache(BigInteger eventGameId, BigInteger partyId, int cycleNumber,
            Integer segmentNumber, Integer stageNumber) {
        if (eventGameId == null || partyId == null) {
            logger.warn("eventGameId 或 partyId 不能为空");
            return 0;
        }

        // 1. 构造缓存 key
        String cacheKey = TournamentCacheKeyUtils.getTournamentChallengeCountKey(eventGameId, partyId,
                TournamentParticipantType.PARTY, cycleNumber, segmentNumber, stageNumber);

        // 2. 缓存过期时间（例如：1天）
        Duration duration = Duration.ofDays(TournamentCacheDurationConstant.TOURNAMENT_CHALLENGE_COUNT_DURATION);

        // 3. 缓存未命中时查 DB
        Integer count = redisClient.get(cacheKey, null, Integer.class, id -> {
            return tournamentChallengeRepository.countByEventGameIdAndParticipantIdAndCycleNumberAndSegmentNumberAndStageNumber(
                    eventGameId, partyId, cycleNumber, segmentNumber, stageNumber);
        }, duration);

        return count != null ? count : 0;
    }

    public List<TournamentChallengeRecord> findCurrentStageGroupParticipantMaxRecords(BigInteger eventGameId, Integer cycleNumber, List<BigInteger> participantIds, TournamentParticipantType participantType, TournamentChallengeStatus status) {
        var tournamentChallengeRecords = tournamentChallengeRepository.findByEventGameIdAndCycleNumberAndParticipantIdInAndParticipantTypeAndStatus(eventGameId, cycleNumber, participantIds, participantType, status);
        return CollectionUtils.transformToList(tournamentChallengeRecords.stream().collect(Collectors.toMap(
                TournamentChallengeRecord::getParticipantId,
                Function.identity(),
                BinaryOperator.maxBy(Comparator.comparing(TournamentChallengeRecord::getFinalScore))
        ))
                .values(), Function.identity());
    }

    public Optional<BigDecimal> findMinPositiveScore(BigInteger eventGameId, Integer cycleNumber, BigInteger groupId) {
        return tournamentChallengeRepository.findFirstByEventGameIdAndCycleNumberAndGroupIdAndFinalScoreGreaterThanOrderByFinalScoreAsc(eventGameId, cycleNumber, groupId,
                BigDecimal.ZERO, TournamentChallengeRecordOnlyFinalScoreView.class).map(TournamentChallengeRecordOnlyFinalScoreView::getFinalScore);
    }
}
