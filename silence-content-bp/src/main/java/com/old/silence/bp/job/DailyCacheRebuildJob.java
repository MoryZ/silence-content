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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author moryzang
 */
@Component
@JobExecutor(name = "dailyCacheRebuildJob")
public class DailyCacheRebuildJob {

    private static final Logger log = LoggerFactory.getLogger(DailyCacheRebuildJob.class);

    private static final int PAGE_SIZE = 1000;

    private final TournamentConfigClient tournamentConfigClient;

    private final TournamentParticipationRecordClient tournamentParticipationRecordClient;

    private final RedissonClient redissonClient;

    public DailyCacheRebuildJob(TournamentConfigClient tournamentConfigClient,
                                TournamentParticipationRecordClient tournamentParticipationRecordClient,
                                RedissonClient redissonClient) {
        this.tournamentConfigClient = tournamentConfigClient;
        this.tournamentParticipationRecordClient = tournamentParticipationRecordClient;
        this.redissonClient = redissonClient;
    }

    public ExecuteResult jobExecute(JobArgs jobArgs) {
        log.info("dailyCacheRebuildJob start, args={}", JacksonMapper.getSharedInstance().toJson(jobArgs));
        TournamentConfigQuery query = new TournamentConfigQuery();
        List<TournamentConfigView> configs = tournamentConfigClient
                .query(query, PageRequest.of(0, PAGE_SIZE), TournamentConfigView.class)
                .getContent();

        int rebuilt = 0;
        for (TournamentConfigView config : configs) {
            rebuildSingleTournamentCache(config);
            rebuilt++;
        }
        return ExecuteResult.success("dailyCacheRebuildJob done, rebuilt=" + rebuilt);
    }

    private void rebuildSingleTournamentCache(TournamentConfigView config) {
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

        RBucket<String> bucket = redissonClient.getBucket(TournamentCacheKeys.rankingList(config.getEventGameId(), null));
        bucket.set(JacksonMapper.getSharedInstance().toJson(rankings), 1, TimeUnit.DAYS);

        log.info("dailyCacheRebuildJob rebuilt ranking cache, eventGameId={}, participantCount={}, topParticipantId={}",
                config.getEventGameId(),
                participants.size(),
                participants.isEmpty() ? "N/A" : participants.get(0).getParticipantId());
    }

}
