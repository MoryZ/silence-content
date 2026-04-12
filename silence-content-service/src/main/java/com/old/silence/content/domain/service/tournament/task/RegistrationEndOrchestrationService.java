package com.old.silence.content.domain.service.tournament.task;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Service;
import com.old.silence.content.api.cache.TournamentCacheKeys;
import com.old.silence.content.api.vo.TournamentConfigView;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentRobotInstanceType;
import com.old.silence.content.domain.enums.tournament.TournamentStageType;
import com.old.silence.content.domain.model.tournament.TournamentConfig;
import com.old.silence.content.domain.model.tournament.TournamentGroup;
import com.old.silence.content.domain.model.tournament.TournamentGroupRecord;
import com.old.silence.content.domain.model.tournament.TournamentParticipationRecord;
import com.old.silence.content.domain.repository.tournament.TournamentConfigRepository;
import com.old.silence.content.domain.repository.tournament.TournamentGroupRecordRepository;
import com.old.silence.content.domain.repository.tournament.TournamentGroupRepository;
import com.old.silence.content.domain.repository.tournament.TournamentParticipationRecordRepository;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 报名截止编排服务（service侧）
 */
@Service
public class RegistrationEndOrchestrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationEndOrchestrationService.class);

    private static final int PAGE_SIZE = 500;

    private final TournamentConfigRepository tournamentConfigRepository;

    private final TournamentParticipationRecordRepository tournamentParticipationRecordRepository;

    private final TournamentGroupRepository tournamentGroupRepository;

    private final TournamentGroupRecordRepository tournamentGroupRecordRepository;

    private final TournamentTaskManageService tournamentTaskManageService;

    private final RedissonClient redissonClient;

    public RegistrationEndOrchestrationService(TournamentConfigRepository tournamentConfigRepository,
                                               TournamentParticipationRecordRepository tournamentParticipationRecordRepository,
                                               TournamentGroupRepository tournamentGroupRepository,
                                               TournamentGroupRecordRepository tournamentGroupRecordRepository,
                                               TournamentTaskManageService tournamentTaskManageService,
                                               RedissonClient redissonClient) {
        this.tournamentConfigRepository = tournamentConfigRepository;
        this.tournamentParticipationRecordRepository = tournamentParticipationRecordRepository;
        this.tournamentGroupRepository = tournamentGroupRepository;
        this.tournamentGroupRecordRepository = tournamentGroupRecordRepository;
        this.tournamentTaskManageService = tournamentTaskManageService;
        this.redissonClient = redissonClient;
    }

    public int handleRegistrationEnd() {
        Instant now = Instant.now();
        int handled = 0;
        for (TournamentConfigView config : loadConfigs()) {
            if (!isRegistrationEnded(config, now)) {
                continue;
            }
            try {
                handled += processSingleTournament(config, now);
            } catch (Exception ex) {
                log.error("registration-end orchestration failed, tournamentId={}, eventGameId={}",
                        config.getId(), config.getEventGameId(), ex);
            }
        }
        return handled;
    }

    private List<TournamentConfigView> loadConfigs() {
        return tournamentConfigRepository
                .findByCriteria(Criteria.empty(), PageRequest.of(0, PAGE_SIZE), TournamentConfigView.class)
                .getContent();
    }

    private boolean isRegistrationEnded(TournamentConfigView config, Instant now) {
        return config.getRegistrationEndTime() != null
                && now.isAfter(config.getRegistrationEndTime())
                && config.getTournamentEndTime() != null
                && now.isBefore(config.getTournamentEndTime());
    }

    private int processSingleTournament(TournamentConfigView config, Instant now) {
        int realCount = countRealParticipants(config.getEventGameId());
        int maxParticipants = config.getMaxParticipants() == null ? realCount : config.getMaxParticipants();
        int requiredRobotCount = Math.max(0, maxParticipants - realCount);

        if (Boolean.TRUE.equals(config.getRobotEnabled()) && requiredRobotCount > 0) {
            createRobotParticipationRecords(config.getEventGameId(), requiredRobotCount, now);
        }

        int totalParticipants = realCount + requiredRobotCount;
        List<BigInteger> groupIds = createGroups(config, totalParticipants, now);
        bindParticipantsToGroups(config.getEventGameId(), groupIds);

        int createdTaskCount = tournamentTaskManageService.initSettlementTasks(config.getId());
        log.info("registration-end orchestration success, tournamentId={}, eventGameId={}, realCount={}, robotCount={}, groups={}, createdTaskCount={}",
                config.getId(), config.getEventGameId(), realCount, requiredRobotCount, groupIds.size(), createdTaskCount);
        return 1;
    }

    private int countRealParticipants(BigInteger eventGameId) {
        Criteria criteria = Criteria.where("event_game_id").is(eventGameId)
                .and("participant_type").is(TournamentParticipantType.MEMBER)
                .and("status").is(TournamentParticipantStatus.REGISTERED);
        return tournamentParticipationRecordRepository
                .findByCriteria(criteria, PageRequest.of(0, PAGE_SIZE), TournamentParticipationRecord.class)
                .getContent()
                .size();
    }

    private void createRobotParticipationRecords(BigInteger eventGameId, int requiredRobotCount, Instant now) {
        for (int i = 0; i < requiredRobotCount; i++) {
            TournamentParticipationRecord record = new TournamentParticipationRecord();
            record.setEventGameId(eventGameId);
            record.setParticipantType(TournamentParticipantType.ROBOT);
            record.setStatus(TournamentParticipantStatus.REGISTERED);
            record.setParticipantId("RBT-" + TournamentRobotInstanceType.TODO.name() + "-" + eventGameId + "-" + i);
            record.setRegistrationTime(now);
            tournamentParticipationRecordRepository.create(record);
        }
    }

    private List<BigInteger> createGroups(TournamentConfigView config, int totalParticipants, Instant now) {
        int groupSize = config.getGroupSize() == null || config.getGroupSize() <= 0 ? 20 : config.getGroupSize();
        int groupCount = Math.max(1, (int) Math.ceil((double) totalParticipants / groupSize));
        List<BigInteger> ids = new ArrayList<>(groupCount);
        for (int i = 1; i <= groupCount; i++) {
            TournamentGroup group = new TournamentGroup();
            group.setEventGameId(config.getEventGameId());
            group.setStageType(TournamentStageType.SESSION);
            group.setStageNumber(1);
            group.setGroupDate(LocalDate.ofInstant(now, ZoneOffset.UTC));
            group.setGroupNumber(i);
            group.setTierName("default");
            group.setTierOrder(1);
            tournamentGroupRepository.create(group);
            ids.add(group.getId());
        }
        return ids;
    }

    private void bindParticipantsToGroups(BigInteger eventGameId, List<BigInteger> groupIds) {
        if (groupIds.isEmpty()) {
            return;
        }

        RMap<String, String> groupAssignments = redissonClient.getMap(TournamentCacheKeys.groupAssignment(eventGameId));
        groupAssignments.clear();

        Criteria criteria = Criteria.where("event_game_id").is(eventGameId)
                .and("status").is(TournamentParticipantStatus.REGISTERED);
        List<TournamentParticipationRecord> participants = tournamentParticipationRecordRepository
                .findByCriteria(criteria, PageRequest.of(0, PAGE_SIZE), TournamentParticipationRecord.class)
                .getContent();

        int idx = 0;
        for (TournamentParticipationRecord participant : participants) {
            TournamentGroupRecord record = new TournamentGroupRecord();
            record.setGroupId(groupIds.get(idx % groupIds.size()));
            record.setParticipantId(participant.getParticipantId());
            record.setParticipantType(participant.getParticipantType());
            tournamentGroupRecordRepository.create(record);
            groupAssignments.put(participant.getParticipantId(), String.valueOf(record.getGroupId()));
            idx++;
        }
        groupAssignments.expire(7, TimeUnit.DAYS);
    }
}