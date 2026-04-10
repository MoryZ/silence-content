package com.old.silence.bp.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import com.old.silence.content.api.cache.TournamentCacheKeys;
import com.old.silence.content.api.TournamentConfigClient;
import com.old.silence.content.api.TournamentGroupClient;
import com.old.silence.content.api.TournamentGroupRecordClient;
import com.old.silence.content.api.TournamentParticipationRecordClient;
import com.old.silence.content.api.dto.TournamentConfigQuery;
import com.old.silence.content.api.dto.TournamentGroupCommand;
import com.old.silence.content.api.dto.TournamentGroupRecordCommand;
import com.old.silence.content.api.dto.TournamentParticipationRecordCommand;
import com.old.silence.content.api.dto.TournamentParticipationRecordQuery;
import com.old.silence.content.api.vo.TournamentConfigView;
import com.old.silence.content.api.vo.TournamentParticipationRecordView;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentRobotInstanceType;
import com.old.silence.content.domain.enums.tournament.TournamentStageType;
import com.old.silence.job.client.core.annotation.JobExecutor;
import com.old.silence.job.client.core.dto.JobArgs;
import com.old.silence.job.common.client.dto.ExecuteResult;
import com.old.silence.json.JacksonMapper;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author moryzang
 */
@Component
@JobExecutor(name = "registrationEndJob")
public class RegistrationEndJob {

    private static final Logger log = LoggerFactory.getLogger(RegistrationEndJob.class);

    private static final int PAGE_SIZE = 500;

    private final TournamentConfigClient tournamentConfigClient;

    private final TournamentParticipationRecordClient tournamentParticipationRecordClient;

    private final TournamentGroupClient tournamentGroupClient;

    private final TournamentGroupRecordClient tournamentGroupRecordClient;

    private final RedissonClient redissonClient;

    public RegistrationEndJob(TournamentConfigClient tournamentConfigClient,
                              TournamentParticipationRecordClient tournamentParticipationRecordClient,
                              TournamentGroupClient tournamentGroupClient,
                              TournamentGroupRecordClient tournamentGroupRecordClient,
                              RedissonClient redissonClient) {
        this.tournamentConfigClient = tournamentConfigClient;
        this.tournamentParticipationRecordClient = tournamentParticipationRecordClient;
        this.tournamentGroupClient = tournamentGroupClient;
        this.tournamentGroupRecordClient = tournamentGroupRecordClient;
        this.redissonClient = redissonClient;
    }

    public ExecuteResult jobExecute(JobArgs jobArgs) {
        log.info("registrationEndJob start, args={}", JacksonMapper.getSharedInstance().toJson(jobArgs));
        Instant now = Instant.now();
        int handled = 0;
        for (TournamentConfigView config : loadOpenTournamentConfigs()) {
            if (!isRegistrationEnded(config, now)) {
                continue;
            }
            try {
                handled += processSingleTournament(config, now);
            } catch (Exception ex) {
                log.error("registrationEndJob process failed, eventGameId={}", config.getEventGameId(), ex);
            }
        }
        return ExecuteResult.success("registrationEndJob done, handled=" + handled);
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
        log.info("registrationEndJob success, eventGameId={}, realCount={}, robotCount={}, groups={}",
                config.getEventGameId(), realCount, requiredRobotCount, groupIds.size());
        return 1;
    }

    private List<TournamentConfigView> loadOpenTournamentConfigs() {
        TournamentConfigQuery query = new TournamentConfigQuery();
        return tournamentConfigClient.query(query, PageRequest.of(0, PAGE_SIZE), TournamentConfigView.class).getContent();
    }

    private boolean isRegistrationEnded(TournamentConfigView config, Instant now) {
        return config.getRegistrationEndTime() != null
                && now.isAfter(config.getRegistrationEndTime())
                && config.getTournamentEndTime() != null
                && now.isBefore(config.getTournamentEndTime());
    }

    private int countRealParticipants(BigInteger eventGameId) {
        TournamentParticipationRecordQuery query = new TournamentParticipationRecordQuery();
        query.setEventGameId(eventGameId);
        query.setParticipantType(TournamentParticipantType.MEMBER);
        query.setStatus(TournamentParticipantStatus.REGISTERED);
        return tournamentParticipationRecordClient
                .query(query, PageRequest.of(0, PAGE_SIZE), TournamentParticipationRecordView.class)
                .getContent().size();
    }

    private void createRobotParticipationRecords(BigInteger eventGameId, int requiredRobotCount, Instant now) {
        for (int i = 0; i < requiredRobotCount; i++) {
            TournamentParticipationRecordCommand command = new TournamentParticipationRecordCommand();
            command.setEventGameId(eventGameId);
            command.setParticipantType(TournamentParticipantType.ROBOT);
            command.setStatus(TournamentParticipantStatus.REGISTERED);
            command.setParticipantId("RBT-" + TournamentRobotInstanceType.TODO.name() + "-" + eventGameId + "-" + i);
            command.setRegistrationTime(now);
            tournamentParticipationRecordClient.create(command);
        }
    }

    private List<BigInteger> createGroups(TournamentConfigView config, int totalParticipants, Instant now) {
        int groupSize = config.getGroupSize() == null || config.getGroupSize() <= 0 ? 20 : config.getGroupSize();
        int groupCount = Math.max(1, (int) Math.ceil((double) totalParticipants / groupSize));
        List<BigInteger> ids = new ArrayList<>(groupCount);
        for (int i = 1; i <= groupCount; i++) {
            TournamentGroupCommand command = new TournamentGroupCommand();
            command.setEventGameId(config.getEventGameId());
            command.setStageType(TournamentStageType.SESSION);
            command.setStageNumber(1);
            command.setGroupDate(LocalDate.ofInstant(now, java.time.ZoneOffset.UTC));
            command.setGroupNumber(i);
            command.setTierName("default");
            command.setTierOrder(1);
            ids.add(tournamentGroupClient.create(command));
        }
        return ids;
    }

    private void bindParticipantsToGroups(BigInteger eventGameId, List<BigInteger> groupIds) {
        if (groupIds.isEmpty()) {
            return;
        }

        RMap<String, String> groupAssignments = redissonClient.getMap(TournamentCacheKeys.groupAssignment(eventGameId));
        groupAssignments.clear();

        TournamentParticipationRecordQuery query = new TournamentParticipationRecordQuery();
        query.setEventGameId(eventGameId);
        query.setStatus(TournamentParticipantStatus.REGISTERED);
        List<TournamentParticipationRecordView> participants = tournamentParticipationRecordClient
                .query(query, PageRequest.of(0, PAGE_SIZE), TournamentParticipationRecordView.class)
                .getContent();

        int idx = 0;
        for (TournamentParticipationRecordView participant : participants) {
            TournamentGroupRecordCommand command = new TournamentGroupRecordCommand();
            command.setGroupId(groupIds.get(idx % groupIds.size()));
            command.setParticipantId(participant.getParticipantId());
            command.setParticipantType(participant.getParticipantType());
            tournamentGroupRecordClient.create(command);
            groupAssignments.put(participant.getParticipantId(), String.valueOf(command.getGroupId()));
            idx++;
        }
        groupAssignments.expire(7, TimeUnit.DAYS);
    }

}
