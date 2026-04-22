package com.old.silence.content.domain.service.tournament;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.old.silence.content.api.tournament.tournament.dto.TournamentGroupInfoCacheDTO;
import com.old.silence.content.api.tournament.tournament.dto.TournamentGroupQuery;
import com.old.silence.content.api.tournament.tournament.dto.TournamentParticipantGroupCacheDto;
import com.old.silence.content.api.tournament.tournament.dto.TournamentParticipantGroupDto;
import com.old.silence.content.domain.common.TournamentCacheDurationConstant;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentStageType;
import com.old.silence.content.util.TournamentCacheKeyUtils;
import com.old.silence.content.domain.model.TournamentGroup;
import com.old.silence.content.domain.repository.tournament.TournamentGroupRecordRepository;
import com.old.silence.content.domain.repository.tournament.TournamentGroupRepository;
import com.old.silence.content.infrastructure.cache.RedisClient;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;
import com.old.silence.json.JacksonMapper;

import java.math.BigInteger;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TournamentGroupDomainService {

    private static final Logger logger = LoggerFactory.getLogger(TournamentGroupDomainService.class);

    private final TournamentGroupRepository tournamentGroupRepository;

    private final TournamentGroupRecordRepository tournamentGroupRecordRepository;

    private final RedisClient redisClient;

    private final JacksonMapper jacksonMapper;

    public TournamentGroupDomainService(TournamentGroupRepository tournamentGroupRepository,
            TournamentGroupRecordRepository tournamentGroupRecordRepository, RedisClient redisClient,
            JacksonMapper jacksonMapper) {
        this.tournamentGroupRepository = tournamentGroupRepository;
        this.tournamentGroupRecordRepository = tournamentGroupRecordRepository;
        this.redisClient = redisClient;
        this.jacksonMapper = jacksonMapper;
    }

    public TournamentParticipantGroupCacheDto findParticipantGroupWithCache(BigInteger eventGameId,
                                                                            BigInteger participantId, TournamentParticipantType participantType, TournamentStageType stageType,
                                                                            Integer stageNumber) {
        var tournamentParticipantGroupKey = TournamentCacheKeyUtils.getTournamentParticipantGroupKey(eventGameId,
                participantId, participantType, stageType, stageNumber);

        TournamentParticipantGroupCacheDto participantGroupCache;
        var participantGroupCacheStr = redisClient.getStrValue(tournamentParticipantGroupKey);
        if (StringUtils.isBlank(participantGroupCacheStr)) {
            var participantGroup = findParticipantGroup(eventGameId, participantId, participantType, stageType,
                    stageNumber);
            participantGroupCache = tournamentParticipantGroupCache(tournamentParticipantGroupKey, participantGroup);
            return participantGroupCache;
        }

        participantGroupCache = jacksonMapper.fromJson(participantGroupCacheStr,
                TournamentParticipantGroupCacheDto.class);
        return participantGroupCache;
    }

    public TournamentParticipantGroupDto findParticipantGroup(BigInteger eventGameId, BigInteger participantId,
                                                              TournamentParticipantType participantType, TournamentStageType stageType, Integer stageNumber) {
        return tournamentGroupRepository.findParticipantGroup(eventGameId, participantId, participantType, stageType,
                stageNumber);
    }

    private TournamentParticipantGroupCacheDto tournamentParticipantGroupCache(String cacheKey,
            TournamentParticipantGroupDto participantGroup) {
        if (participantGroup == null) {
            return null;
        }

        var participantGroupCacheDto = buildTournamentParticipantGroupCacheDto(participantGroup);
        var participantGroupCacheJson = jacksonMapper.toJson(participantGroupCacheDto);
        redisClient.set(cacheKey, participantGroupCacheJson,
                TournamentCacheDurationConstant.TOURNAMENT_PARTICIPANT_GROUP_DURATION, TimeUnit.MINUTES);

        return participantGroupCacheDto;
    }

    private TournamentParticipantGroupCacheDto buildTournamentParticipantGroupCacheDto(
            TournamentParticipantGroupDto tournamentGroup) {
        var participantGroup = new TournamentParticipantGroupCacheDto();
        participantGroup.setGroupId(tournamentGroup.getId());
        participantGroup.setTierName(tournamentGroup.getTierName());
        participantGroup.setTierIcon(tournamentGroup.getTierIcon());
        return participantGroup;
    }

    public <T> Page<T> query(TournamentGroupQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, TournamentGroup.class);
        return tournamentGroupRepository.findByCriteria(criteria, pageable, projectionType);
    }

    public BigInteger create(TournamentGroup tournamentGroup) {
        tournamentGroupRepository.create(tournamentGroup);
        return tournamentGroup.getId();
    }

    /**
     * 获取小组id对应的段位名
     */
    public TournamentGroupInfoCacheDTO getGroupInfoWithCache(BigInteger eventGameId, BigInteger groupId) {
        String cacheKey = TournamentCacheKeyUtils.getTournamentGroupNameKey(eventGameId, groupId);
        Duration duration = Duration.ofDays(TournamentCacheDurationConstant.TOURNAMENT_GROUP_NAME_DURATION);
        return redisClient.get(cacheKey, groupId, TournamentGroupInfoCacheDTO.class,
                id -> loadGroupInfoFromDb(eventGameId, id), duration);

    }

    private TournamentGroupInfoCacheDTO loadGroupInfoFromDb(BigInteger eventGameId, BigInteger groupId) {
        return tournamentGroupRepository.findByIdAndEventGameId(groupId, eventGameId).map(
                        dto -> new TournamentGroupInfoCacheDTO(dto.getTierName(), dto.getTierOrder(),
                                dto.getTierImageUrl()))
                .orElse(null);
    }

    public List<TournamentGroup> findByEventGameIdAndStageTypeAndStageNumberAndGroupDate(BigInteger eventGameId, TournamentStageType tournamentStageType, Integer cycleNumber) {
        return tournamentGroupRepository.findByEventGameIdAndStageTypeAndStageNumber(eventGameId, tournamentStageType, cycleNumber);
    }
}
