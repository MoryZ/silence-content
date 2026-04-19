package com.old.silence.content.domain.service.tournament.task.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.old.silence.content.domain.enums.tournament.TournamentChallengeStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentScoreType;
import com.old.silence.content.domain.enums.tournament.TournamentStageType;
import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;
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
import com.old.silence.content.domain.service.tournament.TournamentTaskDomainService;
import com.old.silence.content.domain.service.tournament.task.TournamentTaskHandlerStrategy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * 场次结算任务处理器
 *
 * @author EX-ZHANGMENGWEI001
 */
@Component
public class StageSettleTaskHandlerStrategy implements TournamentTaskHandlerStrategy {

    private static final Logger log = LoggerFactory.getLogger(StageSettleTaskHandlerStrategy.class);
    public static final int TOURNAMENT_SCORE_SCALE = 2;

    private final TournamentConfigDomainService tournamentConfigDomainService;
    private final TournamentChallengeDomainService tournamentChallengeDomainService;
    private final TournamentScoreRecordDomainService tournamentScoreRecordDomainService;
    private final TournamentTaskDomainService tournamentTaskDomainService;
    private final TournamentGroupDomainService tournamentGroupDomainService;
    private final TournamentRankingDomainService tournamentRankingDomainService;

    private final TournamentGroupRecordRepository tournamentGroupRecordRepository;


    public StageSettleTaskHandlerStrategy(TournamentConfigDomainService tournamentConfigDomainService,
                                          TournamentChallengeDomainService tournamentChallengeDomainService,
                                          TournamentScoreRecordDomainService tournamentScoreRecordDomainService,
                                          TournamentTaskDomainService tournamentTaskDomainService,
                                          TournamentGroupDomainService tournamentGroupDomainService,
                                          TournamentRankingDomainService tournamentRankingDomainService,
                                          TournamentGroupRecordRepository tournamentGroupRecordRepository) {
        this.tournamentConfigDomainService = tournamentConfigDomainService;
        this.tournamentChallengeDomainService = tournamentChallengeDomainService;
        this.tournamentScoreRecordDomainService = tournamentScoreRecordDomainService;
        this.tournamentTaskDomainService = tournamentTaskDomainService;
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
        // 1.判断前置任务是否完成

        var hasPrevTask = tournamentTaskDomainService.findById(tournamentTask.getTournamentId(), TournamentTask.class)
                .filter(task -> TournamentTaskStatus.SUCCESS.equals(task.getStatus())).isPresent();
        if (hasPrevTask) {
            log.info("1.前置任务未完成");
            return;
        }

        // 2.判断当前场次是否真的已结束
        var tournamentConfig = tournamentConfigDomainService.findByEventGameId(tournamentTask.getEventGameId(), TournamentConfig.class).get();
        var cycleStageConfig = tournamentConfig.getCycleStageConfig();
       /*  if (!TournamentStageCalculationService.isUntilStageEndTime(CollectionUtils.firstElement(cycleStageConfig.getStageConfigs()).map(StageConfig::getEndTime).get())) {
            log.info("2.任务未到执行时间");
            return;
        } */

        // 3.获取所有分组
        var tournamentGroups = tournamentGroupDomainService.findByEventGameIdAndStageTypeAndStageNumberAndGroupDate(
        eventGameId, TournamentStageType.CYCLE, tournamentTask.getCycleNo());
        if (CollectionUtils.isEmpty(tournamentGroups)) {
            log.warn("本赛事暂无分组 跳过");
            return;
        }
        log.info("3.获取赛事所有分组, 分组数：{}", CollectionUtils.size(tournamentGroups));

        try {
            // 4.遍历所有的组 查找组的挑战记录

            for (TournamentGroup tournamentGroup : tournamentGroups) {
                var tournamentChallengeRecords = tournamentChallengeDomainService.findCurrentStageGroupParticipantMaxRecords(tournamentTask.getEventGameId(),
                        tournamentTask.getCycleNo(), tournamentGroup.getId(), TournamentParticipantType.PARTY, TournamentChallengeStatus.COMPLETED);
                log.info("4.获取所有分组的挑战记录 挑战记录组数：{}", CollectionUtils.size(tournamentChallengeRecords));

                // 5. 取最高分,生成得分记录 ，暂时写在此类
                List<TournamentScoreRecord> allScoreRecords = new ArrayList<>();
                for (TournamentChallengeRecord tournamentChallengeRecord : tournamentChallengeRecords) {
                    var tournamentScoreRecord = buildTournamentScoreRecord(eventGameId, tournamentChallengeRecord.getParticipantId(), tournamentChallengeRecord.getParticipantType(),
                            tournamentChallengeRecord.getFinalScore(), tournamentTask.getStageNo(), tournamentTask.getSegmentNo(), tournamentTask.getCycleNo(), tournamentGroup.getId());
                    allScoreRecords.add(tournamentScoreRecord);
                }
                log.info("5.取最高分 构造得分记录");

                // 6.根据配置系数 给机器人计算得分
                var robotParticipantRecords = tournamentGroupRecordRepository.findByGroupIdAndParticipantType(tournamentGroup.getId(), TournamentParticipantType.ROBOT, TournamentGroupRecord.class);
                if (CollectionUtils.isEmpty(robotParticipantRecords)) {
                    log.info("无需补机器人 得分记录");
                    continue;
                }
                log.info("6.根据配置系数 给机器人计算得分，机器数量:{}", CollectionUtils.size(robotParticipantRecords));

                // 7.找到最低的非0分 ，如果全是0 只能取0
                var lowerScore = tournamentChallengeRecords.stream()
                        .filter(tournamentChallengeRecord -> tournamentChallengeRecord.getFinalScore().compareTo(BigDecimal.ZERO) >= 0)
                        .findFirst().map(TournamentChallengeRecord::getFinalScore).orElse(BigDecimal.ZERO);

                for (TournamentGroupRecord robotParticipantRecord : robotParticipantRecords) {
                    var robotScore = calculateRobotScore(cycleStageConfig.getRobotScoreCoefficientMin(), cycleStageConfig.getRobotScoreCoefficientMax(), lowerScore
                            , TOURNAMENT_SCORE_SCALE);
                    var tournamentScoreRecord = buildTournamentScoreRecord(eventGameId, robotParticipantRecord.getParticipantId(), TournamentParticipantType.ROBOT, robotScore,
                            tournamentTask.getStageNo(), tournamentTask.getSegmentNo(), tournamentTask.getCycleNo(), tournamentGroup.getId());
                    allScoreRecords.add(tournamentScoreRecord);
                }
                log.info("7.根据配置系数 给机器人计算得分，机器人得分记录数:{}", CollectionUtils.size(robotParticipantRecords));

                // 8.批量创建得分记录
                tournamentScoreRecordDomainService.bulkCreate(allScoreRecords);
                log.info("8.批量创建得分记录(人和机器人的)");

                // 9.创建组的排行榜
                tournamentRankingDomainService.buildGroupRanking(tournamentGroup.getId(), allScoreRecords);
                log.info("9.创建组排行榜");
            }
        } catch (Exception e) {
            log.error("StageSettleTaskHandlerStrategy run occur error :{}", e.getMessage());
        }


        // 10.更新任务状态
        tournamentTaskDomainService.updateStatusAsLock(tournamentTask.getId(), TournamentTaskStatus.SUCCESS, tournamentTask.getStatus());
        log.info("10.更新任务状态");

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
