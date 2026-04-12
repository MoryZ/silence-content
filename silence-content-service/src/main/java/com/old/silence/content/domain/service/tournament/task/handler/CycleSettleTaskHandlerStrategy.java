package com.old.silence.content.domain.service.tournament.task.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentScoreType;
import com.old.silence.content.domain.model.tournament.TournamentParticipationRecord;
import com.old.silence.content.domain.model.tournament.TournamentScoreRecord;
import com.old.silence.content.domain.model.tournament.TournamentTask;
import com.old.silence.content.domain.repository.tournament.TournamentParticipationRecordRepository;
import com.old.silence.content.domain.repository.tournament.TournamentScoreRecordRepository;
import com.old.silence.content.domain.service.tournament.task.TournamentTaskHandlerStrategy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 周期结算任务处理器
 *
 * @author moryzang
 */
@Component
public class CycleSettleTaskHandlerStrategy implements TournamentTaskHandlerStrategy {

    private static final Logger log = LoggerFactory.getLogger(CycleSettleTaskHandlerStrategy.class);

    private static final int PAGE_SIZE = 2000;

    private final TournamentScoreRecordRepository tournamentScoreRecordRepository;

    private final TournamentParticipationRecordRepository tournamentParticipationRecordRepository;

    public CycleSettleTaskHandlerStrategy(TournamentScoreRecordRepository tournamentScoreRecordRepository,
                                          TournamentParticipationRecordRepository tournamentParticipationRecordRepository) {
        this.tournamentScoreRecordRepository = tournamentScoreRecordRepository;
        this.tournamentParticipationRecordRepository = tournamentParticipationRecordRepository;
    }

    @Override
    public TournamentTaskType getTaskType() {
        return TournamentTaskType.CYCLE_SETTLE;
    }

    @Override
    public void execute(TournamentTask task) {
        List<TournamentScoreRecord> segmentScores = loadSegmentScores(task);
        Map<String, ParticipantCycleScore> cycleScoreMap = sumCycleScores(segmentScores);

        int upserted = 0;
        for (ParticipantCycleScore score : cycleScoreMap.values()) {
            upsertCycleScore(task, score);
            refreshParticipationTotalScore(task, score);
            upserted++;
        }

        log.info("CycleSettleTaskHandler done, id={}, tournamentId={}, cycleNo={}, segmentScoreCount={}, participantCount={}",
            task.getId(), task.getTournamentId(), task.getCycleNo(), segmentScores.size(), upserted);
    }

    private List<TournamentScoreRecord> loadSegmentScores(TournamentTask task) {
        Criteria criteria = Criteria.where("event_game_id").is(task.getEventGameId())
                .and("score_type").is(TournamentScoreType.SEGMENT)
            .and("cycle_number").is(task.getCycleNo());
        return tournamentScoreRecordRepository
                .findByCriteria(criteria, PageRequest.of(0, PAGE_SIZE), TournamentScoreRecord.class)
                .getContent();
    }

    private Map<String, ParticipantCycleScore> sumCycleScores(List<TournamentScoreRecord> segmentScores) {
        Map<String, ParticipantCycleScore> scoreMap = new HashMap<>();
        for (TournamentScoreRecord record : segmentScores) {
            String key = buildParticipantKey(record.getParticipantId(), record.getParticipantType());
            BigDecimal score = record.getScore() == null ? BigDecimal.ZERO : record.getScore();

            ParticipantCycleScore existing = scoreMap.get(key);
            if (existing == null) {
                scoreMap.put(key, new ParticipantCycleScore(
                        record.getParticipantId(),
                        record.getParticipantType(),
                        record.getCycleNumber(),
                        record.getGroupId(),
                        score));
                continue;
            }
            scoreMap.put(key, existing.withAddedScore(score));
        }
        return scoreMap;
    }

    private void upsertCycleScore(TournamentTask task, ParticipantCycleScore score) {
        Criteria criteria = Criteria.where("event_game_id").is(task.getEventGameId())
                .and("participant_id").is(score.participantId())
                .and("participant_type").is(score.participantType())
                .and("score_type").is(TournamentScoreType.CYCLE)
                .and("cycle_number").is(task.getCycleNo());
        Optional<TournamentScoreRecord> existing = tournamentScoreRecordRepository
                .findByCriteria(criteria, PageRequest.of(0, 1), TournamentScoreRecord.class)
                .stream()
                .findFirst();

        TournamentScoreRecord target = existing.orElseGet(TournamentScoreRecord::new);
        target.setEventGameId(task.getEventGameId());
        target.setParticipantId(score.participantId());
        target.setParticipantType(score.participantType());
        target.setScoreType(TournamentScoreType.CYCLE);
        target.setCycleNumber(task.getCycleNo());
        target.setGroupId(score.groupId());
        target.setScore(score.score());

        if (existing.isPresent()) {
            tournamentScoreRecordRepository.update(target);
            return;
        }
        tournamentScoreRecordRepository.create(target);
    }

    private void refreshParticipationTotalScore(TournamentTask task, ParticipantCycleScore cycleScore) {
        Criteria criteria = Criteria.where("event_game_id").is(task.getEventGameId())
                .and("participant_id").is(cycleScore.participantId())
                .and("participant_type").is(cycleScore.participantType());

        Optional<TournamentParticipationRecord> optionalRecord = tournamentParticipationRecordRepository
                .findByCriteria(criteria, PageRequest.of(0, 1), TournamentParticipationRecord.class)
                .stream()
                .findFirst();

        if (optionalRecord.isEmpty()) {
            log.warn("CycleSettleTaskHandler participation not found, tournamentId={}, participantId={}, participantType={}",
                    task.getTournamentId(), cycleScore.participantId(), cycleScore.participantType());
            return;
        }

        TournamentParticipationRecord record = optionalRecord.get();
        Criteria cycleScoreCriteria = Criteria.where("event_game_id").is(task.getEventGameId())
            .and("participant_id").is(cycleScore.participantId())
            .and("participant_type").is(cycleScore.participantType())
            .and("score_type").is(TournamentScoreType.CYCLE);
        BigDecimal totalScore = tournamentScoreRecordRepository
            .findByCriteria(cycleScoreCriteria, PageRequest.of(0, PAGE_SIZE), TournamentScoreRecord.class)
            .getContent()
            .stream()
            .map(item -> item.getScore() == null ? BigDecimal.ZERO : item.getScore())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        record.setTotalScore(totalScore);
        tournamentParticipationRecordRepository.update(record);
    }

    private String buildParticipantKey(String participantId, TournamentParticipantType participantType) {
        return participantId + "#" + participantType.name();
    }

    private record ParticipantCycleScore(String participantId,
                                         TournamentParticipantType participantType,
                                         Integer cycleNumber,
                                         java.math.BigInteger groupId,
                                         BigDecimal score) {
        private ParticipantCycleScore withAddedScore(BigDecimal additionalScore) {
            return new ParticipantCycleScore(participantId, participantType, cycleNumber, groupId,
                    score.add(additionalScore));
        }
    }
}
