package com.old.silence.bp.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import com.old.silence.content.api.cache.TournamentCacheKeys;
import com.old.silence.content.api.TournamentConfigClient;
import com.old.silence.content.api.TournamentParticipationRecordClient;
import com.old.silence.content.api.TournamentTaskClient;
import com.old.silence.content.api.dto.TournamentConfigQuery;
import com.old.silence.content.api.dto.TournamentParticipationRecordQuery;
import com.old.silence.content.api.vo.TournamentCRankingView;
import com.old.silence.content.api.vo.TournamentConfigView;
import com.old.silence.content.api.vo.TournamentParticipationRecordView;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;
import com.old.silence.job.client.core.annotation.JobExecutor;
import com.old.silence.job.client.core.dto.JobArgs;
import com.old.silence.job.common.client.dto.ExecuteResult;
import com.old.silence.json.JacksonMapper;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author moryzang
 */
@Component
@JobExecutor(name = "finalSettlementJob")
public class FinalSettlementJob {

    private static final Logger log = LoggerFactory.getLogger(FinalSettlementJob.class);

    private static final int PAGE_SIZE = 1000;

    private static final Duration SETTLEMENT_BUFFER = Duration.ofMinutes(30);

    private final TournamentConfigClient tournamentConfigClient;

    private final TournamentParticipationRecordClient tournamentParticipationRecordClient;

    private final TournamentTaskClient tournamentTaskClient;

    private final RedissonClient redissonClient;

    public FinalSettlementJob(TournamentConfigClient tournamentConfigClient,
                              TournamentParticipationRecordClient tournamentParticipationRecordClient,
                              TournamentTaskClient tournamentTaskClient,
                              RedissonClient redissonClient) {
        this.tournamentConfigClient = tournamentConfigClient;
        this.tournamentParticipationRecordClient = tournamentParticipationRecordClient;
        this.tournamentTaskClient = tournamentTaskClient;
        this.redissonClient = redissonClient;
    }

    public ExecuteResult jobExecute(JobArgs jobArgs) {
        log.info("finalSettlementJob start, args={}", JacksonMapper.getSharedInstance().toJson(jobArgs));
        int settled = 0;
        Instant now = Instant.now();

        TournamentConfigQuery query = new TournamentConfigQuery();
        List<TournamentConfigView> configs = tournamentConfigClient
                .query(query, PageRequest.of(0, PAGE_SIZE), TournamentConfigView.class)
                .getContent();

        for (TournamentConfigView config : configs) {
            if (!inSettlementWindow(config, now)) {
                continue;
            }
            if (tournamentTaskClient.hasUnfinishedTasks(config.getEventGameId())) {
                log.info("finalSettlementJob skip, unfinished tournament tasks exist, eventGameId={}",
                        config.getEventGameId());
                continue;
            }
            settleSingleTournament(config);
            settled++;
        }
        return ExecuteResult.success("finalSettlementJob done, settled=" + settled);
    }

    private boolean inSettlementWindow(TournamentConfigView config, Instant now) {
        if (config.getTournamentEndTime() == null) {
            return false;
        }
        Instant end = config.getTournamentEndTime();
        return now.isAfter(end) && now.isBefore(end.plus(SETTLEMENT_BUFFER));
    }

    private void settleSingleTournament(TournamentConfigView config) {
        TournamentParticipationRecordQuery query = new TournamentParticipationRecordQuery();
        query.setEventGameId(config.getEventGameId());
        query.setStatus(TournamentParticipantStatus.REGISTERED);
        List<TournamentParticipationRecordView> participants = tournamentParticipationRecordClient
                .query(query, PageRequest.of(0, PAGE_SIZE), TournamentParticipationRecordView.class)
                .getContent();

        participants.sort(Comparator.comparing(
                item -> item.getTotalScore() == null ? BigDecimal.ZERO : item.getTotalScore(),
                Comparator.reverseOrder()));

        List<TournamentCRankingView> rankings = new ArrayList<>(participants.size());
        int rankNo = 1;
        for (TournamentParticipationRecordView participant : participants) {
            TournamentCRankingView ranking = new TournamentCRankingView();
            ranking.setEventGameId(config.getEventGameId());
            ranking.setParticipantId(participant.getParticipantId());
            ranking.setParticipantType(participant.getParticipantType());
            ranking.setScore(participant.getTotalScore() == null ? BigDecimal.ZERO : participant.getTotalScore());
            ranking.setRankNo(rankNo++);
            ranking.setNickname(participant.getParticipantId());
            rankings.add(ranking);
        }
        RBucket<String> bucket = redissonClient.getBucket(TournamentCacheKeys.finalRankingList(config.getEventGameId()));
        bucket.set(JacksonMapper.getSharedInstance().toJson(rankings), 7, TimeUnit.DAYS);

        if (!participants.isEmpty()) {
            log.info("finalSettlementJob ranking ready, eventGameId={}, winner={}, score={}",
                    config.getEventGameId(),
                    participants.get(0).getParticipantId(),
                    participants.get(0).getTotalScore());
        } else {
            log.info("finalSettlementJob ranking ready, eventGameId={}, winner=N/A", config.getEventGameId());
        }

        log.info("finalSettlementJob reward dispatch pending, eventGameId={}", config.getEventGameId());
    }

}
