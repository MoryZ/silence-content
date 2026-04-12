package com.old.silence.content.domain.service.tournament.task.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentScoreType;
import com.old.silence.content.domain.model.tournament.TournamentScoreRecord;
import com.old.silence.content.domain.model.tournament.TournamentTask;
import com.old.silence.content.domain.repository.tournament.TournamentScoreRecordRepository;
import com.old.silence.content.domain.service.tournament.task.TournamentTaskHandlerStrategy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 片结算任务处理器
 *
 * @author moryzang
 */
@Component
public class SegmentSettleTaskHandlerStrategy implements TournamentTaskHandlerStrategy {

    private static final Logger log = LoggerFactory.getLogger(SegmentSettleTaskHandlerStrategy.class);

    private static final int PAGE_SIZE = 2000;

    private final TournamentScoreRecordRepository tournamentScoreRecordRepository;

    public SegmentSettleTaskHandlerStrategy(TournamentScoreRecordRepository tournamentScoreRecordRepository) {
        this.tournamentScoreRecordRepository = tournamentScoreRecordRepository;
    }

    @Override
    public TournamentTaskType getTaskType() {
        return TournamentTaskType.SEGMENT_SETTLE;
    }

    @Override
    public void execute(TournamentTask task) {
        List<TournamentScoreRecord> stageScores = loadStageScores(task);
        Map<String, ParticipantSegmentScore> segmentScoreMap = sumSegmentScores(stageScores);

        int upserted = 0;
        for (ParticipantSegmentScore score : segmentScoreMap.values()) {
            upsertSegmentScore(task, score);
            upserted++;
        }

        log.info("SegmentSettleTaskHandler done, id={}, tournamentId={}, segmentNo={}, stageScoreCount={}, participantCount={}",
            task.getId(), task.getTournamentId(), task.getPeriodNo(), stageScores.size(), upserted);
    }

    private List<TournamentScoreRecord> loadStageScores(TournamentTask task) {
        Criteria criteria = Criteria.where("event_game_id").is(task.getTournamentId())
                .and("score_type").is(TournamentScoreType.STAGE)
                .and("segment_number").is(task.getPeriodNo());
        return tournamentScoreRecordRepository
                .findByCriteria(criteria, PageRequest.of(0, PAGE_SIZE), TournamentScoreRecord.class)
                .getContent();
    }

    private Map<String, ParticipantSegmentScore> sumSegmentScores(List<TournamentScoreRecord> stageScores) {
        Map<String, ParticipantSegmentScore> scoreMap = new HashMap<>();
        for (TournamentScoreRecord record : stageScores) {
            String key = buildParticipantKey(record.getParticipantId(), record.getParticipantType());
            BigDecimal score = record.getScore() == null ? BigDecimal.ZERO : record.getScore();

            ParticipantSegmentScore existing = scoreMap.get(key);
            if (existing == null) {
                scoreMap.put(key, new ParticipantSegmentScore(
                        record.getParticipantId(),
                        record.getParticipantType(),
                        record.getCycleNumber(),
                        record.getSegmentNumber(),
                        record.getGroupId(),
                        score));
                continue;
            }
            scoreMap.put(key, existing.withAddedScore(score));
        }
        return scoreMap;
    }

    private void upsertSegmentScore(TournamentTask task, ParticipantSegmentScore score) {
        Criteria criteria = Criteria.where("event_game_id").is(task.getTournamentId())
                .and("participant_id").is(score.participantId())
                .and("participant_type").is(score.participantType())
                .and("score_type").is(TournamentScoreType.SEGMENT)
                .and("segment_number").is(task.getPeriodNo());
        Optional<TournamentScoreRecord> existing = tournamentScoreRecordRepository
                .findByCriteria(criteria, PageRequest.of(0, 1), TournamentScoreRecord.class)
                .stream()
                .findFirst();

        TournamentScoreRecord target = existing.orElseGet(TournamentScoreRecord::new);
        target.setEventGameId(task.getTournamentId());
        target.setParticipantId(score.participantId());
        target.setParticipantType(score.participantType());
        target.setScoreType(TournamentScoreType.SEGMENT);
        target.setCycleNumber(score.cycleNumber());
        target.setSegmentNumber(task.getPeriodNo());
        target.setGroupId(score.groupId());
        target.setScore(score.score());

        if (existing.isPresent()) {
            tournamentScoreRecordRepository.update(target);
            return;
        }
        tournamentScoreRecordRepository.create(target);
    }

    private String buildParticipantKey(String participantId, TournamentParticipantType participantType) {
        return participantId + "#" + participantType.name();
    }

    private record ParticipantSegmentScore(String participantId,
                                           TournamentParticipantType participantType,
                                           Integer cycleNumber,
                                           Integer segmentNumber,
                                           java.math.BigInteger groupId,
                                           BigDecimal score) {
        private ParticipantSegmentScore withAddedScore(BigDecimal additionalScore) {
            return new ParticipantSegmentScore(participantId, participantType, cycleNumber, segmentNumber, groupId,
                    score.add(additionalScore));
        }
    }
}
