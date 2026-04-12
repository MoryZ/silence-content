package com.old.silence.content.domain.service.tournament.task.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentScoreType;
import com.old.silence.content.domain.model.tournament.TournamentChallengeRecord;
import com.old.silence.content.domain.model.tournament.TournamentScoreRecord;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;
import com.old.silence.content.domain.model.tournament.TournamentTask;
import com.old.silence.content.domain.repository.tournament.TournamentChallengeRecordRepository;
import com.old.silence.content.domain.repository.tournament.TournamentScoreRecordRepository;
import com.old.silence.content.domain.service.tournament.task.TournamentTaskHandlerStrategy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 场次结算任务处理器
 *
 * @author moryzang
 */
@Component
public class StageSettleTaskHandlerStrategy implements TournamentTaskHandlerStrategy {

    private static final Logger log = LoggerFactory.getLogger(StageSettleTaskHandlerStrategy.class);

    private static final int PAGE_SIZE = 2000;

    private final TournamentChallengeRecordRepository tournamentChallengeRecordRepository;

    private final TournamentScoreRecordRepository tournamentScoreRecordRepository;

    public StageSettleTaskHandlerStrategy(TournamentChallengeRecordRepository tournamentChallengeRecordRepository,
                                          TournamentScoreRecordRepository tournamentScoreRecordRepository) {
        this.tournamentChallengeRecordRepository = tournamentChallengeRecordRepository;
        this.tournamentScoreRecordRepository = tournamentScoreRecordRepository;
    }

    @Override
    public TournamentTaskType getTaskType() {
        return TournamentTaskType.STAGE_SETTLE;
    }

    @Override
    public void execute(TournamentTask task) {
        List<TournamentChallengeRecord> challengeRecords = loadCompletedChallenges(task);
        Map<String, ParticipantStageScore> participantScoreMap = calculateParticipantMaxStageScore(challengeRecords);
        int upserted = 0;
        for (ParticipantStageScore score : participantScoreMap.values()) {
            upsertStageScore(task, score);
            upserted++;
        }
        log.info("StageSettleTaskHandler done, id={}, tournamentId={}, stageNo={}, challengeCount={}, participantCount={}",
            task.getId(), task.getTournamentId(), task.getStageNo(), challengeRecords.size(), upserted);
    }

    private List<TournamentChallengeRecord> loadCompletedChallenges(TournamentTask task) {
        Criteria criteria = Criteria.where("event_game_id").is(task.getEventGameId())
            .and("stage_number").is(task.getStageNo())
                .and("status").is(TournamentChallengeStatus.COMPLETED);
        return tournamentChallengeRecordRepository
                .findByCriteria(criteria, PageRequest.of(0, PAGE_SIZE), TournamentChallengeRecord.class)
                .getContent();
    }

    private Map<String, ParticipantStageScore> calculateParticipantMaxStageScore(List<TournamentChallengeRecord> challengeRecords) {
        Map<String, ParticipantStageScore> scoreMap = new HashMap<>();
        for (TournamentChallengeRecord record : challengeRecords) {
            BigDecimal score = record.getFinalScore() == null ? BigDecimal.ZERO : record.getFinalScore();
            String key = buildParticipantKey(record.getParticipantId(), record.getParticipantType());
            ParticipantStageScore existing = scoreMap.get(key);
            if (existing == null || score.compareTo(existing.score()) > 0) {
                scoreMap.put(key, new ParticipantStageScore(
                        record.getParticipantId(),
                        record.getParticipantType(),
                        record.getCycleNumber(),
                        record.getSegmentNumber(),
                        record.getStageNumber(),
                        record.getGroupId(),
                        score));
            }
        }
        return scoreMap;
    }

    private void upsertStageScore(TournamentTask task, ParticipantStageScore score) {
        Criteria criteria = Criteria.where("event_game_id").is(task.getEventGameId())
                .and("participant_id").is(score.participantId())
                .and("participant_type").is(score.participantType())
                .and("score_type").is(TournamentScoreType.STAGE)
                .and("stage_number").is(task.getStageNo());
        Optional<TournamentScoreRecord> existing = tournamentScoreRecordRepository
                .findByCriteria(criteria, PageRequest.of(0, 1), TournamentScoreRecord.class)
                .stream()
                .findFirst();

        TournamentScoreRecord target = existing.orElseGet(TournamentScoreRecord::new);
        target.setEventGameId(task.getEventGameId());
        target.setParticipantId(score.participantId());
        target.setParticipantType(score.participantType());
        target.setScoreType(TournamentScoreType.STAGE);
        target.setCycleNumber(score.cycleNumber());
        target.setSegmentNumber(score.segmentNumber());
        target.setStageNumber(task.getStageNo());
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

    private record ParticipantStageScore(String participantId,
                                         TournamentParticipantType participantType,
                                         Integer cycleNumber,
                                         Integer segmentNumber,
                                         Integer stageNumber,
                                         java.math.BigInteger groupId,
                                         BigDecimal score) {
    }
}
