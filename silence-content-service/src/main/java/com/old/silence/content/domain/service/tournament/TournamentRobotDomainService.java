package com.old.silence.content.domain.service.tournament;

import org.springframework.stereotype.Service;
import com.old.silence.content.api.config.TournamentConfigurationProperties;
import com.old.silence.content.api.tournament.tournament.dto.TournamentRobotCacheDTO;
import com.old.silence.content.domain.common.TournamentCacheDurationConstant;
import com.old.silence.content.domain.enums.tournament.TournamentRobotStatus;
import com.old.silence.content.domain.model.TournamentRobotInstance;
import com.old.silence.content.domain.repository.tournament.TournamentRobotInstanceRepository;
import com.old.silence.content.infrastructure.cache.RedisClient;
import com.old.silence.content.util.TournamentCacheKeyUtils;

import java.math.BigInteger;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author moryzang
 */
@Service
public class TournamentRobotDomainService {

    private final TournamentRobotInstanceRepository robotInstanceRepository;

    private final RedisClient redisClient;

    private final TournamentConfigurationProperties tournamentConfigurationProperties;

    public TournamentRobotDomainService(TournamentRobotInstanceRepository robotInstanceRepository,
                                        RedisClient redisClient, TournamentConfigurationProperties tournamentConfigurationProperties) {
        this.robotInstanceRepository = robotInstanceRepository;
        this.redisClient = redisClient;
        this.tournamentConfigurationProperties = tournamentConfigurationProperties;
    }

    public List<TournamentRobotInstance> initRobotInstances(BigInteger eventGameId, int appendRobotCount) {
        var tournamentRobotTemplates = tournamentConfigurationProperties.getRobotTemplates();
        List<TournamentRobotInstance> tournamentRobotInstances = new ArrayList<>();
        for (int i = 0; i < appendRobotCount; i++) {
            var circularIndex = i % tournamentRobotTemplates.size();
            var tournamentRobotTemplate = tournamentRobotTemplates.get(circularIndex);

            TournamentRobotInstance tournamentRobotInstance = TournamentRobotInstance.initTournamentRobotInstance(tournamentRobotTemplate);
            tournamentRobotInstance.setEventGameId(eventGameId);
            tournamentRobotInstances.add(tournamentRobotInstance);
        }
        robotInstanceRepository.bulkCreate(tournamentRobotInstances);
        return tournamentRobotInstances;
    }

    public TournamentRobotCacheDTO getRobotInfoWithCache(BigInteger eventGameId, BigInteger robotId) {
        String cacheKey = TournamentCacheKeyUtils.getRobotInfoKey(eventGameId, robotId);
        Duration duration = Duration.ofDays(TournamentCacheDurationConstant.TOURNAMENT_ROBOT_INFO_DURATION);

        return redisClient.get(cacheKey, robotId, TournamentRobotCacheDTO.class,
                id -> loadRobotInfoFromDb(eventGameId, id), duration);
    }

    private TournamentRobotCacheDTO loadRobotInfoFromDb(BigInteger eventGameId, BigInteger robotId) {
        return robotInstanceRepository.findByEventGameIdAndRobotIdAndStatus(eventGameId, robotId,
                TournamentRobotStatus.ACTIVE).map(
                instance -> new TournamentRobotCacheDTO(instance.getNickname(), instance.getAvatarUrl())).orElse(null);
    }
}
