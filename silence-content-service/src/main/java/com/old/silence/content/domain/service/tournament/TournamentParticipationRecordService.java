package com.old.silence.content.domain.service.tournament;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.old.silence.content.api.dto.TournamentRegisterCacheDto;
import com.old.silence.content.api.dto.TournamentRegisterCommand;
import com.old.silence.content.api.vo.TournamentRegisterVo;
import com.old.silence.content.domain.common.BizErrorCode;
import com.old.silence.content.domain.common.MarketingBizException;
import com.old.silence.content.domain.common.TournamentCacheDurationConstant;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.infrastructure.cache.RedisClient;
import com.old.silence.content.infrastructure.lock.RedissonLock;
import com.old.silence.content.infrastructure.remote.HsmsClient;
import com.old.silence.content.util.TournamentCacheKeyUtils;
import com.old.silence.content.util.TournamentLockKeyUtils;
import com.old.silence.json.JacksonMapper;
import com.old.silence.content.domain.model.TournamentParticipationRecord;
import com.old.silence.content.domain.repository.tournament.TournamentParticipationRecordRepository;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TournamentParticipationRecordService {

    private static final Logger logger = LoggerFactory.getLogger(TournamentParticipationRecordService.class);

    private final TournamentParticipationRecordRepository tournamentParticipationRecordRepository;
    private final RedisClient redisClient;
    private final RedissonLock redissonLock;
    private final JacksonMapper jacksonMapper;
    private final TournamentConfigCacheDomainService tournamentConfigCacheDomainService;
    private final HsmsClient hsmsClient;

    public TournamentParticipationRecordService(TournamentParticipationRecordRepository tournamentParticipationRecordRepository,
                                                RedisClient redisClient,
                                                RedissonLock redissonLock,
                                                JacksonMapper jacksonMapper,
                                                TournamentConfigCacheDomainService tournamentConfigCacheDomainService,
                                                HsmsClient hsmsClient) {
        this.tournamentParticipationRecordRepository = tournamentParticipationRecordRepository;
        this.redisClient = redisClient;
        this.redissonLock = redissonLock;
        this.jacksonMapper = jacksonMapper;
        this.tournamentConfigCacheDomainService = tournamentConfigCacheDomainService;
        this.hsmsClient = hsmsClient;
    }

    public int create(TournamentParticipationRecord tournamentParticipationRecord){
        return tournamentParticipationRecordRepository.create(tournamentParticipationRecord);
    }

    public TournamentParticipationRecord findTournamentParticipation(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType participantType){
        return tournamentParticipationRecordRepository.findByEventGameIdAndParticipantIdAndParticipantType(eventGameId, participantId, participantType);
    }

    public TournamentRegisterCacheDto findTournamentParticipationWithCache(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType participantType){
        var tournamentParticipationKey = TournamentCacheKeyUtils.getTournamentParticipationKey(
                eventGameId,
                participantId,
                participantType);

        var competitionRegistrationStr = redisClient.getStrValue(tournamentParticipationKey);
        TournamentRegisterCacheDto tournamentRegisterCacheDto;
        if(StringUtils.isBlank(competitionRegistrationStr)){
            var tournamentParticipation = findTournamentParticipation(eventGameId, participantId, participantType);
            tournamentRegisterCacheDto = tournamentParticipationCache(tournamentParticipationKey, tournamentParticipation);
            return tournamentRegisterCacheDto;
        }

        tournamentRegisterCacheDto = jacksonMapper.fromJson(competitionRegistrationStr, TournamentRegisterCacheDto.class);
        return tournamentRegisterCacheDto;
    }

    public int countTournamentParticipation(BigInteger eventGameId){
        var tournamentParticipationCountKey = TournamentCacheKeyUtils.getTournamentParticipationCountKey(
                eventGameId);
        var tournamentParticipationCountCache = redisClient.getStrValue(tournamentParticipationCountKey);

        if(StringUtils.isNotBlank(tournamentParticipationCountCache)){
            return Integer.parseInt(tournamentParticipationCountCache);
        }

        logger.warn("tournamentParticipationCount miss,key:{}", tournamentParticipationCountCache);
        var countLockKey = TournamentLockKeyUtils.getTournamentParticipationCountLockKey(eventGameId);
        try {
            redissonLock.lock(countLockKey,
                    TimeUnit.SECONDS,
                    TournamentCacheDurationConstant.TOURNAMENT_LOCK_TIMEOUT);

            tournamentParticipationCountCache = redisClient.getStrValue(tournamentParticipationCountKey);
            if(StringUtils.isNotBlank(tournamentParticipationCountCache)){
                return Integer.parseInt(tournamentParticipationCountCache);
            }
            var count = tournamentParticipationRecordRepository.countByEventGameId(eventGameId);
            logger.info("tournamentParticipationCount, eventGameId:{},count:{}",eventGameId, count);
            flushTournamentParticipationCount(tournamentParticipationCountKey, count);
            return count;
        }finally {
            redissonLock.unlock(countLockKey);
        }
    }

    @Transactional
    public TournamentRegisterVo tournamentRegister(TournamentRegisterCommand command) {
        logger.info("competitionRegister,command:{}", jacksonMapper.toJson(command));
        var participantId = command.getPartyId();
        var participantType = TournamentParticipantType.PARTY;
        var eventGameId = command.getEventGameId();
        var lockCompetitionRegistrationKey = TournamentLockKeyUtils.getTournamentParticipationLockKey(
                command.getEventGameId(),
                participantId,
                participantType);

        try {
            var lockResult = redissonLock.tryLock(lockCompetitionRegistrationKey,
                    TimeUnit.SECONDS,
                    TournamentCacheDurationConstant.TOURNAMENT_LOCK_TIMEOUT);
            if(!lockResult){
                logger.info("competitionRegister in process");
                throw new MarketingBizException(BizErrorCode.TOURNAMENT_REGISTRATION_OPERATE_FREQUENTLY);
            }

            // 获取赛事配置
            var tournamentConfig = tournamentConfigCacheDomainService.queryTournamentConfig(eventGameId);
            // 校验赛事时间，状态，报名时间
            var registrationTime = Instant.now();
            TournamentStageCalculationService.checkMarketingEvent(
                    registrationTime,
                    tournamentConfig.getEvent().getStartTime(),
                    tournamentConfig.getEvent().getEndTime(),
                    tournamentConfig.getEvent().getStatus()
            );
            TournamentStageCalculationService.checkTournamentRegisterTime(
                    registrationTime,
                    tournamentConfig.getRegistrationStartTime(),
                    tournamentConfig.getRegistrationEndTime());

            // 医健测校验活力go权益
            var vitalityGoInfo = hsmsClient.checkCompetitionActivitiesRegister(eventGameId, participantId);
            if(vitalityGoInfo == null){
                throw new MarketingBizException(BizErrorCode.TOURNAMENT_REGISTRATION_WITHOUT_VITALITY_GO_BENEFICIARY);
            }

            //是否重复报名
            boolean existParticipationRecord = existTournamentParticipationRecord(eventGameId, participantId, participantType);
            if(existParticipationRecord){
                throw new MarketingBizException(BizErrorCode.TOURNAMENT_REGISTRATION_REPEAT);
            }
            var maxParticipants = tournamentConfig.getMaxParticipants();
            //是否超过最大报名人数
            int participationCount = countTournamentParticipation(eventGameId);
            if(participationCount > maxParticipants){
                throw new MarketingBizException(BizErrorCode.TOURNAMENT_REGISTRATION_LIMIT);
            }

             var vitalityGoDistChannel = vitalityGoInfo.getDistChannel();
            var tournamentParticipation = createAndCache(
                    eventGameId, participantId, participantType, vitalityGoDistChannel, maxParticipants, registrationTime);
            return buildTournamentRegisterVo(tournamentParticipation);
        }finally {
            redissonLock.unlock(lockCompetitionRegistrationKey);
        }
    }

    public TournamentParticipationRecord createAndCache(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType participantType, String vitalityGoDistChannel, Integer maxParticipants, Instant registrationTime){
        var tournamentParticipation = new TournamentParticipationRecord();
        tournamentParticipation.setParticipantId(participantId);
        tournamentParticipation.setParticipantType(participantType);
        tournamentParticipation.setStatus(TournamentParticipantStatus.REGISTERED);
        tournamentParticipation.setEventGameId(eventGameId);
        tournamentParticipation.setTotalScore(BigDecimal.ZERO);
        tournamentParticipation.setRegistrationTime(registrationTime);
        var attributes = new HashMap<String, Object>();
        attributes.put("vitalityGoDistChannel", vitalityGoDistChannel);
        tournamentParticipation.setAttributes(attributes);
        create(tournamentParticipation);
        incrementTournamentParticipationCount(tournamentParticipation.getEventGameId(), maxParticipants);
        tournamentParticipationCache(
                tournamentParticipation.getEventGameId(),
                tournamentParticipation.getParticipantId(),
                tournamentParticipation.getParticipantType(),
                tournamentParticipation
        );
        return tournamentParticipation;
    }

    public boolean existTournamentParticipationRecord(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType participantType){
        var tournamentRegisterCacheDto = findTournamentParticipationWithCache(eventGameId, participantId, participantType);
        return tournamentRegisterCacheDto != null && tournamentRegisterCacheDto.getId() != null;
    }

    private TournamentRegisterCacheDto tournamentParticipationCache(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType participantType, TournamentParticipationRecord tournamentParticipationRecord) {
        var tournamentParticipationKey = TournamentCacheKeyUtils.getTournamentParticipationKey(
                eventGameId,
                participantId,
                participantType);
        return tournamentParticipationCache(tournamentParticipationKey, tournamentParticipationRecord);
    }

    private TournamentRegisterCacheDto tournamentParticipationCache(String tournamentParticipationKey, TournamentParticipationRecord tournamentParticipationRecord){

        var tournamentRegisterCache = "{}";
        TournamentRegisterCacheDto tournamentRegisterCacheDto = null;
        if(tournamentParticipationRecord != null){
            tournamentRegisterCacheDto = buildTournamentRegisterCacheDto(tournamentParticipationRecord);
            tournamentRegisterCache = jacksonMapper.toJson(tournamentRegisterCacheDto);
        }
        redisClient.set(tournamentParticipationKey,
                tournamentRegisterCache,
                TournamentCacheDurationConstant.TOURNAMENT_PARTICIPATION_DURATION,
                TimeUnit.DAYS);

        return tournamentRegisterCacheDto;
    }

    private void incrementTournamentParticipationCount(BigInteger eventGameId, Integer maxParticipants){
        var tournamentParticipationCountKey = TournamentCacheKeyUtils.getTournamentParticipationCountKey(
                eventGameId);
        var currentParticipantCount = redisClient.increment(tournamentParticipationCountKey, 1);
        if(maxParticipants != null && currentParticipantCount > maxParticipants){
            redisClient.set(tournamentParticipationCountKey, maxParticipants.toString());
            throw new MarketingBizException(BizErrorCode.TOURNAMENT_REGISTRATION_LIMIT);
        }
    }

    public void flushTournamentParticipationCount(String key, Integer count){
        redisClient.set(key, String.valueOf(count));
    }


    private TournamentRegisterCacheDto buildTournamentRegisterCacheDto(TournamentParticipationRecord tournamentParticipationRecord){
        var tournamentRegisterCacheDto = new TournamentRegisterCacheDto();
        tournamentRegisterCacheDto.setId(tournamentParticipationRecord.getId());
        tournamentRegisterCacheDto.setRegistrationTime(tournamentParticipationRecord.getRegistrationTime());
        return tournamentRegisterCacheDto;
    }

    private TournamentRegisterVo buildTournamentRegisterVo(TournamentParticipationRecord tournamentParticipationRecord){
        var tournamentRegisterVo = new TournamentRegisterVo();
        tournamentRegisterVo.setParticipantRecordId(tournamentParticipationRecord.getId());
        tournamentRegisterVo.setParticipantId(tournamentParticipationRecord.getParticipantId());
        tournamentRegisterVo.setParticipantType(tournamentParticipationRecord.getParticipantType());
        tournamentRegisterVo.setRegistrationTime(tournamentParticipationRecord.getRegistrationTime());
        return tournamentRegisterVo;
    }

    public int bulkCreateAndFlushRedisCache(BigInteger eventGameId, Integer participantCount, List<TournamentParticipationRecord> tournamentParticipationRecords) {
        var rowsAffected = tournamentParticipationRecordRepository.bulkCreate(tournamentParticipationRecords);
        var tournamentParticipationCountKey = TournamentCacheKeyUtils.getTournamentParticipationCountKey(
                eventGameId);
        flushTournamentParticipationCount(tournamentParticipationCountKey, participantCount);
        return rowsAffected;
    }

    public <T> List<T> queryRobotRecord(BigInteger eventGameId, BigInteger id, Pageable pageable, Class<T> projectionType) {
        var participantType = TournamentParticipantType.ROBOT;
        return tournamentParticipationRecordRepository.findByEventGameIdAndParticipantTypeAndIdGreaterThan(eventGameId, participantType, id, pageable, projectionType);
    }
}
