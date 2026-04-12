package com.old.silence.content.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.cache.TournamentCacheKeys;
import com.old.silence.content.api.dto.TournamentChallengeCompleteCommand;
import com.old.silence.content.api.dto.TournamentChallengeStartCommand;
import com.old.silence.content.api.dto.TournamentUserRegisterCommand;
import com.old.silence.content.api.vo.TournamentCListItemView;
import com.old.silence.content.api.vo.TournamentCRankingView;
import com.old.silence.content.api.vo.TournamentConfigView;
import com.old.silence.content.api.vo.TournamentUserStatusView;
import com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;
import com.old.silence.content.domain.enums.tournament.TournamentRankingType;
import com.old.silence.content.domain.model.tournament.TournamentChallengeRecord;
import com.old.silence.content.domain.model.tournament.TournamentParticipationRecord;
import com.old.silence.content.domain.repository.tournament.TournamentChallengeRecordRepository;
import com.old.silence.content.domain.repository.tournament.TournamentConfigRepository;
import com.old.silence.content.domain.repository.tournament.TournamentParticipationRecordRepository;
import com.old.silence.json.JacksonMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

@Validated
@RestController
@RequestMapping("/api/v1")
public class TournamentCResource implements TournamentCService {

    private static final int LARGE_PAGE_SIZE = 1000;

    private final TournamentConfigRepository tournamentConfigRepository;

    private final TournamentParticipationRecordRepository tournamentParticipationRecordRepository;

    private final TournamentChallengeRecordRepository tournamentChallengeRecordRepository;

    private final RedissonClient redissonClient;

    private final ObjectMapper objectMapper;

    public TournamentCResource(TournamentConfigRepository tournamentConfigRepository,
                               TournamentParticipationRecordRepository tournamentParticipationRecordRepository,
                               TournamentChallengeRecordRepository tournamentChallengeRecordRepository,
                               RedissonClient redissonClient) {
        this.tournamentConfigRepository = tournamentConfigRepository;
        this.tournamentParticipationRecordRepository = tournamentParticipationRecordRepository;
        this.tournamentChallengeRecordRepository = tournamentChallengeRecordRepository;
        this.redissonClient = redissonClient;
        this.objectMapper = JacksonMapper.getSharedInstance().unwrap();
    }

    @Override
    public Page<TournamentCListItemView> listTournaments(Pageable pageable) {
        RBucket<String> bucket = redissonClient.getBucket(TournamentCacheKeys.tournamentList());
        List<TournamentCListItemView> cached = readListBucket(bucket, new TypeReference<List<TournamentCListItemView>>() { });
        if (cached != null) {
            return toPage(cached, pageable);
        }

        List<TournamentCListItemView> tournaments = tournamentConfigRepository
                .findByCriteria(Criteria.empty(), PageRequest.of(0, LARGE_PAGE_SIZE), TournamentConfigView.class)
                .stream()
                .map(this::toTournamentListItem)
                .toList();
        writeBucket(bucket, tournaments, 10, TimeUnit.MINUTES);
        return toPage(tournaments, pageable);
    }

    @Override
    public BigInteger register(BigInteger eventGameId, TournamentUserRegisterCommand command) {
        TournamentParticipationRecord record = new TournamentParticipationRecord();
        record.setEventGameId(eventGameId);
        record.setParticipantId(command.getParticipantId());
        record.setParticipantType(command.getParticipantType());
        record.setStatus(TournamentParticipantStatus.REGISTERED);
        record.setRegistrationTime(Instant.now());
        validateModifyingResult(tournamentParticipationRecordRepository.create(record));
        redissonClient.getBucket(TournamentCacheKeys.userStatus(eventGameId, command.getParticipantId())).delete();
        return record.getId();
    }

    @Override
    public TournamentUserStatusView getUserStatus(BigInteger eventGameId, String participantId) {
        RBucket<String> bucket = redissonClient.getBucket(TournamentCacheKeys.userStatus(eventGameId, participantId));
        TournamentUserStatusView cached = readBucket(bucket, TournamentUserStatusView.class);
        if (cached != null) {
            return cached;
        }

        Criteria participationCriteria = Criteria.where("event_game_id").is(eventGameId)
                .and("participant_id").is(participantId);

        Optional<com.old.silence.content.api.vo.TournamentParticipationRecordView> participation =
                tournamentParticipationRecordRepository
                        .findByCriteria(participationCriteria, PageRequest.of(0, 1), com.old.silence.content.api.vo.TournamentParticipationRecordView.class)
                        .stream().findFirst();

        Criteria challengeCriteria = Criteria.where("event_game_id").is(eventGameId)
                .and("participant_id").is(participantId);
        long challengeCount = tournamentChallengeRecordRepository
                .findByCriteria(challengeCriteria, PageRequest.of(0, LARGE_PAGE_SIZE), com.old.silence.content.api.vo.TournamentChallengeRecordView.class)
                .getTotalElements();

        Integer currentRankNo = resolveCurrentRankNo(eventGameId, participantId);

        TournamentUserStatusView statusView = new TournamentUserStatusView();
        statusView.setRegistered(participation.isPresent());
        statusView.setRegistrationStatus(participation.map(com.old.silence.content.api.vo.TournamentParticipationRecordView::getStatus).orElse(null));
        statusView.setTotalChallengeCount(challengeCount);
        statusView.setCurrentRankNo(currentRankNo);
        writeBucket(bucket, statusView, 10, TimeUnit.MINUTES);
        return statusView;
    }

    @Override
    public BigInteger startChallenge(BigInteger eventGameId, TournamentChallengeStartCommand command) {
        TournamentChallengeRecord record = new TournamentChallengeRecord();
        record.setEventGameId(eventGameId);
        record.setParticipantId(command.getParticipantId());
        record.setParticipantType(command.getParticipantType());
        record.setContributorId(command.getContributorId());
        record.setCycleNumber(command.getCycleNumber());
        record.setSegmentNumber(command.getSegmentNumber());
        record.setStageNumber(command.getStageNumber());
        record.setGroupId(command.getGroupId());
        record.setStartTime(Instant.now());
        record.setStatus(command.getStatus() == null ? TournamentChallengeStatus.COMPLETED : command.getStatus());
        validateModifyingResult(tournamentChallengeRecordRepository.create(record));
        redissonClient.getBucket(TournamentCacheKeys.userStatus(eventGameId, command.getParticipantId())).delete();
        return record.getId();
    }

    @Override
    public void completeChallenge(BigInteger eventGameId, BigInteger challengeRecordId, TournamentChallengeCompleteCommand command) {
        TournamentChallengeRecord record = tournamentChallengeRecordRepository
                .findById(challengeRecordId, TournamentChallengeRecord.class)
                .orElseThrow(() -> new IllegalArgumentException("Challenge record not found: " + challengeRecordId));

        if (!eventGameId.equals(record.getEventGameId())) {
            throw new IllegalArgumentException("Challenge record does not belong to eventGameId=" + eventGameId);
        }

        record.setBaseScore(command.getBaseScore());
        record.setFinalScore(command.getFinalScore());
        record.setExerciseDetails(command.getExerciseDetails());
        record.setStatus(command.getStatus());
        record.setEndTime(Instant.now());
        validateModifyingResult(tournamentChallengeRecordRepository.update(record));
        redissonClient.getBucket(TournamentCacheKeys.userStatus(eventGameId, record.getParticipantId())).delete();
        redissonClient.getBucket(TournamentCacheKeys.rankingList(eventGameId, null)).delete();
    }

    @Override
    public Page<TournamentCRankingView> getRankings(BigInteger eventGameId,
                                                    TournamentRankingType rankingType,
                                                    Pageable pageable) {
        RBucket<String> bucket = redissonClient.getBucket(TournamentCacheKeys.rankingList(eventGameId, rankingType));
        List<TournamentCRankingView> cached = readListBucket(bucket, new TypeReference<List<TournamentCRankingView>>() { });
        if (cached != null) {
            return toPage(cached, pageable);
        }

        Criteria criteria = Criteria.where("event_game_id").is(eventGameId);
        List<TournamentCRankingView> rankings = tournamentParticipationRecordRepository
            .findByCriteria(criteria, PageRequest.of(0, LARGE_PAGE_SIZE), com.old.silence.content.api.vo.TournamentParticipationRecordView.class)
                .stream()
            .map(item -> toRealtimeRankingView(item, rankingType))
            .sorted(Comparator.comparing(
                item -> item.getScore() == null ? BigDecimal.ZERO : item.getScore(),
                Comparator.reverseOrder()))
                .toList();
        int rankNo = 1;
        for (TournamentCRankingView ranking : rankings) {
            ranking.setRankNo(rankNo++);
        }
        writeBucket(bucket, rankings, 10, TimeUnit.MINUTES);
        return toPage(rankings, pageable);
    }

    @Override
    public Optional<TournamentCRankingView> getCurrentUserRanking(BigInteger eventGameId,
                                                                  String participantId,
                                                                  TournamentRankingType rankingType) {
        RBucket<String> bucket = redissonClient.getBucket(TournamentCacheKeys.userRanking(eventGameId, participantId, rankingType));
        TournamentCRankingView cached = readBucket(bucket, TournamentCRankingView.class);
        if (cached != null) {
            return Optional.of(cached);
        }

        Criteria criteria = Criteria.where("event_game_id").is(eventGameId)
            .and("participant_id").is(participantId);
        Optional<com.old.silence.content.api.vo.TournamentParticipationRecordView> participation = tournamentParticipationRecordRepository
            .findByCriteria(criteria, PageRequest.of(0, 1), com.old.silence.content.api.vo.TournamentParticipationRecordView.class)
            .stream().findFirst();

        Optional<TournamentCRankingView> ranking = participation.map(item -> {
            TournamentCRankingView view = toRealtimeRankingView(item, rankingType);
            view.setRankNo(resolveCurrentRankNo(eventGameId, participantId));
            return view;
        });
        ranking.ifPresent(value -> writeBucket(bucket, value, 10, TimeUnit.MINUTES));
        return ranking;
    }

    private TournamentCListItemView toTournamentListItem(TournamentConfigView source) {
        TournamentCListItemView target = new TournamentCListItemView();
        target.setId(source.getId());
        target.setEventGameId(source.getEventGameId());
        target.setTournamentType(source.getTournamentType());
        target.setDivisionMode(source.getDivisionMode());
        target.setMatchMode(source.getMatchMode());
        target.setRobotEnabled(source.getRobotEnabled());
        target.setCurrentStage(source.getCurrentStage());
        target.setTotalStages(source.getTotalStages());
        target.setRegistrationStartTime(source.getRegistrationStartTime());
        target.setRegistrationEndTime(source.getRegistrationEndTime());
        target.setTournamentStartTime(source.getTournamentStartTime());
        target.setTournamentEndTime(source.getTournamentEndTime());
        target.setMaxParticipants(source.getMaxParticipants());
        target.setMinParticipants(source.getMinParticipants());
        target.setGroupSize(source.getGroupSize());
        return target;
    }

    private TournamentCRankingView toRealtimeRankingView(com.old.silence.content.api.vo.TournamentParticipationRecordView source,
                                                         TournamentRankingType rankingType) {
        TournamentCRankingView target = new TournamentCRankingView();
        target.setId(source.getId());
        target.setEventGameId(source.getEventGameId());
        target.setGroupId(null);
        target.setParticipantId(source.getParticipantId());
        target.setParticipantType(source.getParticipantType());
        target.setRankingType(rankingType);
        target.setScore(source.getTotalScore() == null ? BigDecimal.ZERO : source.getTotalScore());
        target.setAvatarUrl(null);
        target.setNickname(source.getParticipantId());
        return target;
    }

    private Integer resolveCurrentRankNo(BigInteger eventGameId, String participantId) {
        Criteria criteria = Criteria.where("event_game_id").is(eventGameId)
                .and("status").is(TournamentParticipantStatus.REGISTERED);
        List<com.old.silence.content.api.vo.TournamentParticipationRecordView> participants = tournamentParticipationRecordRepository
                .findByCriteria(criteria, PageRequest.of(0, LARGE_PAGE_SIZE), com.old.silence.content.api.vo.TournamentParticipationRecordView.class)
                .getContent();
        List<com.old.silence.content.api.vo.TournamentParticipationRecordView> sorted = participants.stream()
                .sorted(Comparator.comparing(
                        item -> item.getTotalScore() == null ? BigDecimal.ZERO : item.getTotalScore(),
                        Comparator.reverseOrder()))
                .toList();
        int rankNo = 1;
        for (com.old.silence.content.api.vo.TournamentParticipationRecordView item : sorted) {
            if (participantId.equals(item.getParticipantId())) {
                return rankNo;
            }
            rankNo++;
        }
        return null;
    }

    private <T> T readBucket(RBucket<String> bucket, Class<T> type) {
        if (!bucket.isExists()) {
            return null;
        }
        try {
            return objectMapper.readValue(bucket.get(), type);
        } catch (Exception ex) {
            bucket.delete();
            return null;
        }
    }

    private <T> List<T> readListBucket(RBucket<String> bucket, TypeReference<List<T>> typeReference) {
        if (!bucket.isExists()) {
            return null;
        }
        try {
            return objectMapper.readValue(bucket.get(), typeReference);
        } catch (Exception ex) {
            bucket.delete();
            return null;
        }
    }

    private void writeBucket(RBucket<String> bucket, Object value, long ttl, TimeUnit unit) {
        try {
            bucket.set(objectMapper.writeValueAsString(value), ttl, unit);
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to write redis cache", ex);
        }
    }

    private <T> Page<T> toPage(List<T> items, Pageable pageable) {
        int start = (int) Math.min(pageable.getOffset(), items.size());
        int end = Math.min(start + pageable.getPageSize(), items.size());
        return new PageImpl<>(new ArrayList<>(items.subList(start, end)), pageable, items.size());
    }

}
