package com.old.silence.content.domain.service.tournament.task.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.old.silence.content.api.tournament.tournament.dto.support.CycleStageConfig;
import com.old.silence.content.api.tournament.tournament.dto.support.StageConfig;
import com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentScoreType;
import com.old.silence.content.domain.enums.tournament.TournamentStageType;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;
import com.old.silence.content.domain.service.tournament.TournamentStageCalculationService;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.content.domain.model.TournamentChallengeRecord;
import com.old.silence.content.domain.model.TournamentConfig;
import com.old.silence.content.domain.model.TournamentGroup;
import com.old.silence.content.domain.model.TournamentGroupRecord;
import com.old.silence.content.domain.model.TournamentScoreRecord;
import com.old.silence.content.domain.model.TournamentTask;
import com.old.silence.content.domain.repository.tournament.TournamentGroupRecordRepository;
import com.old.silence.content.domain.service.tournament.TournamentChallengeDomainService;
import com.old.silence.content.domain.service.tournament.TournamentConfigDomainService;
import com.old.silence.content.domain.service.tournament.TournamentGroupDomainService;
import com.old.silence.content.domain.service.tournament.TournamentRankingDomainService;
import com.old.silence.content.domain.service.tournament.TournamentScoreRecordDomainService;
import com.old.silence.content.domain.service.tournament.task.TournamentTaskHandlerStrategy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 场次结算任务处理器
 *
 * @author EX-ZHANGMENGWEI001
 */
@Component
public class StageSettleTaskHandlerStrategy implements TournamentTaskHandlerStrategy {

    private static final Logger log = LoggerFactory.getLogger(StageSettleTaskHandlerStrategy.class);
    public static final int TOURNAMENT_SCORE_SCALE = 2;
    public static final Integer PAGE_SIZE = 1000;

    private final TournamentConfigDomainService tournamentConfigDomainService;
    private final TournamentChallengeDomainService tournamentChallengeDomainService;
    private final TournamentScoreRecordDomainService tournamentScoreRecordDomainService;
    private final TournamentGroupDomainService tournamentGroupDomainService;
    private final TournamentRankingDomainService tournamentRankingDomainService;

    private final TournamentGroupRecordRepository tournamentGroupRecordRepository;


    public StageSettleTaskHandlerStrategy(TournamentConfigDomainService tournamentConfigDomainService,
                                          TournamentChallengeDomainService tournamentChallengeDomainService,
                                          TournamentScoreRecordDomainService tournamentScoreRecordDomainService,
                                          TournamentGroupDomainService tournamentGroupDomainService,
                                          TournamentRankingDomainService tournamentRankingDomainService,
                                          TournamentGroupRecordRepository tournamentGroupRecordRepository) {
        this.tournamentConfigDomainService = tournamentConfigDomainService;
        this.tournamentChallengeDomainService = tournamentChallengeDomainService;
        this.tournamentScoreRecordDomainService = tournamentScoreRecordDomainService;
        this.tournamentGroupDomainService = tournamentGroupDomainService;
        this.tournamentRankingDomainService = tournamentRankingDomainService;
        this.tournamentGroupRecordRepository = tournamentGroupRecordRepository;
    }

    @Override
    public TournamentTaskType getTaskType() {
        return TournamentTaskType.STAGE_SETTLE;
    }

    @Override
    @Transactional
    public void execute(TournamentTask tournamentTask) {
        var eventGameId = tournamentTask.getEventGameId();

        // 1.判断当前场次是否真的已结束
        var tournamentConfig = tournamentConfigDomainService.findByEventGameId(tournamentTask.getEventGameId(), TournamentConfig.class).get();
        var cycleStageConfig = tournamentConfig.getCycleStageConfig();
         if (!TournamentStageCalculationService.isUntilStageEndTime(CollectionUtils.firstElement(cycleStageConfig.getStageConfigs()).map(StageConfig::getEndTime).get())) {
            log.info("1.任务未到执行时间");
            return;
        }

        // 2.获取所有分组
        var tournamentGroups = tournamentGroupDomainService.findByEventGameIdAndStageTypeAndStageNumberAndGroupDate(
        eventGameId, TournamentStageType.CYCLE, tournamentTask.getCycleNo());
        if (CollectionUtils.isEmpty(tournamentGroups)) {
            log.warn("本赛事暂无分组 跳过");
            return;
        }
        log.info("2.获取赛事所有分组, 分组数：{}", CollectionUtils.size(tournamentGroups));

        // 遍历所有的组 查找组的挑战记录
        for (TournamentGroup tournamentGroup : tournamentGroups) {

            // 获取当前组所有已完赛真人的最低非0分
            BigDecimal lowerScore = tournamentChallengeDomainService.findMinPositiveScore(eventGameId, tournamentTask.getCycleNo(), tournamentGroup.getId())
                    .orElse(BigDecimal.ZERO);

            List<TournamentScoreRecord> allScoreRecords = new ArrayList<>();

            BigInteger lastId = BigInteger.ZERO;
            PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "id"));

            var batchAllParticipants = tournamentGroupRecordRepository.findByGroupIdAndIdGraterThan(tournamentGroup.getId(), lastId, pageRequest, TournamentGroupRecord.class);

            while(CollectionUtils.isNotEmpty(batchAllParticipants)) {
                var tournamentChallengeRecords = tournamentChallengeDomainService.findCurrentStageGroupParticipantMaxRecords(tournamentTask.getEventGameId(),
                        tournamentTask.getCycleNo(), CollectionUtils.transformToList(batchAllParticipants, TournamentGroupRecord::getParticipantId), TournamentParticipantType.PARTY, TournamentChallengeStatus.COMPLETED);
                log.info("3.获取所有分组的挑战记录 挑战记录组数：{}", CollectionUtils.size(tournamentChallengeRecords));

                var challengeRecordMap = tournamentChallengeRecords.stream()
                        .collect(Collectors.toMap(TournamentChallengeRecord::getParticipantId, Function.identity()));

                for (TournamentGroupRecord participant : batchAllParticipants) {
                    var tournamentScoreRecord = buildTournamentScoreRecord(tournamentTask, tournamentGroup, participant, challengeRecordMap, cycleStageConfig, lowerScore, eventGameId);
                    allScoreRecords.add(tournamentScoreRecord);
                }

                var lastRecord = batchAllParticipants.get(batchAllParticipants.size() - 1);
                lastId = lastRecord.getId();

                batchAllParticipants = tournamentGroupRecordRepository.findByGroupIdAndIdGraterThan(tournamentGroup.getId(), lastId, pageRequest, TournamentGroupRecord.class);
            }
            log.info("4.组id:{}, 得分记录数:{}", tournamentGroup.getId(), CollectionUtils.size(allScoreRecords));

            // 5.批量创建得分记录
            tournamentScoreRecordDomainService.bulkCreate(allScoreRecords);
            log.info("5.批量创建得分记录(人和机器人的)");

            // 6.创建组的排行榜
            tournamentRankingDomainService.buildGroupRanking(tournamentGroup.getId(), allScoreRecords);
            log.info("6.创建组排行榜");
        }

    }

    private TournamentScoreRecord buildTournamentScoreRecord(TournamentTask tournamentTask, TournamentGroup tournamentGroup, TournamentGroupRecord participant, Map<BigInteger, TournamentChallengeRecord> challengeRecordMap, CycleStageConfig cycleStageConfig, BigDecimal lowerScore, BigInteger eventGameId) {
        BigDecimal score = BigDecimal.ZERO;
        if (participant.getParticipantType() == TournamentParticipantType.PARTY) {
            // PARTY 选手需要按自身挑战记录赋分，若无挑战记录则得0分
            var challenge = challengeRecordMap.get(participant.getParticipantId());
            if (challenge != null) {
                score = challenge.getFinalScore();
            }
        } else if (participant.getParticipantType() == TournamentParticipantType.ROBOT) {
            // 根据配置系数给机器人计算得分
            score = calculateRobotScore(cycleStageConfig.getRobotScoreCoefficientMin(), cycleStageConfig.getRobotScoreCoefficientMax(), lowerScore, TOURNAMENT_SCORE_SCALE);
        }
        return buildTournamentScoreRecord(eventGameId, participant.getParticipantId(), participant.getParticipantType(), score,
                tournamentTask.getStageNo(), tournamentTask.getSegmentNo(), tournamentTask.getCycleNo(), tournamentGroup.getId());
    }

    private BigDecimal calculateRobotScore(BigDecimal robotScoreCoefficientMin, BigDecimal robotScoreCoefficientMax, BigDecimal finalScore, int scale) {
        var random = ThreadLocalRandom.current().nextDouble(robotScoreCoefficientMin.doubleValue(), robotScoreCoefficientMax.doubleValue());
        return BigDecimal.valueOf(random).multiply(finalScore).setScale(scale, RoundingMode.HALF_EVEN);
    }


    private static TournamentScoreRecord buildTournamentScoreRecord(BigInteger eventGameId, BigInteger participantId, TournamentParticipantType participantType, BigDecimal finalScore,
                                                                    Integer stageNumber, Integer segmentNumber, Integer cycleNumber, BigInteger groupId) {
        var tournamentScoreRecord = new TournamentScoreRecord();
        tournamentScoreRecord.setEventGameId(eventGameId);
        tournamentScoreRecord.setParticipantId(participantId);
        tournamentScoreRecord.setParticipantType(participantType);

        tournamentScoreRecord.setScore(finalScore);
        tournamentScoreRecord.setScoreType(TournamentScoreType.CYCLE);

        tournamentScoreRecord.setStageNumber(stageNumber);
        tournamentScoreRecord.setSegmentNumber(segmentNumber);
        tournamentScoreRecord.setCycleNumber(cycleNumber);
        tournamentScoreRecord.setGroupId(groupId);
        return tournamentScoreRecord;
    }


}
