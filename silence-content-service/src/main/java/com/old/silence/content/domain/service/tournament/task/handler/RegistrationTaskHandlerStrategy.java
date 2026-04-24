package com.old.silence.content.domain.service.tournament.task.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantStatus;
import com.old.silence.content.domain.enums.tournament.TournamentParticipantType;
import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.content.domain.model.TournamentConfig;
import com.old.silence.content.domain.model.TournamentParticipationRecord;
import com.old.silence.content.domain.model.TournamentRobotInstance;
import com.old.silence.content.domain.model.TournamentTask;
import com.old.silence.content.domain.service.tournament.TournamentConfigDomainService;
import com.old.silence.content.domain.service.tournament.TournamentGroupRecordDomainService;
import com.old.silence.content.domain.service.tournament.TournamentParticipationRecordService;
import com.old.silence.content.domain.service.tournament.TournamentRobotDomainService;
import com.old.silence.content.domain.service.tournament.TournamentTaskDomainService;
import com.old.silence.content.domain.service.tournament.task.TournamentTaskHandlerStrategy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;


/**
 * 场次结算任务处理器
 *
 * @author EX-ZHANGMENGWEI001
 */
@Component
public class RegistrationTaskHandlerStrategy implements TournamentTaskHandlerStrategy {

    private static final Logger log = LoggerFactory.getLogger(RegistrationTaskHandlerStrategy.class);

    private final TournamentParticipationRecordService tournamentParticipationRecordService;
    private final TournamentRobotDomainService tournamentRobotDomainService;
    private final TournamentConfigDomainService tournamentConfigDomainService;
    private final TournamentGroupRecordDomainService tournamentGroupRecordDomainService;

    public RegistrationTaskHandlerStrategy(TournamentParticipationRecordService tournamentParticipationRecordService,
                                           TournamentRobotDomainService tournamentRobotDomainService,
                                           TournamentConfigDomainService tournamentConfigDomainService,
                                           TournamentGroupRecordDomainService tournamentGroupRecordDomainService) {
        this.tournamentParticipationRecordService = tournamentParticipationRecordService;
        this.tournamentRobotDomainService = tournamentRobotDomainService;
        this.tournamentConfigDomainService = tournamentConfigDomainService;
        this.tournamentGroupRecordDomainService = tournamentGroupRecordDomainService;
    }

    @Override
    public TournamentTaskType getTaskType() {
        return TournamentTaskType.REGISTRATION_END;
    }

    // TODO 补log
    @Override
    @Transactional
    public void execute(TournamentTask tournamentTask) {
        var eventGameId = tournamentTask.getEventGameId();
        // 1.查询当前参赛人数
        var actualRegistrationCount = tournamentParticipationRecordService.countTournamentParticipation(eventGameId);

        if (actualRegistrationCount == 0) {
            log.info("RegistrationTaskHandler: 无真人报名");
            return;
        }

        var tournamentConfig = tournamentConfigDomainService.findByEventGameId(eventGameId, TournamentConfig.class).get();
        var maxParticipants = tournamentConfig.getMaxParticipants();
        // 判断是否要补机器人
        if (actualRegistrationCount > maxParticipants) {
            // 异常场景
            log.info("RegistrationTaskHandler: 实际报名人数 超过最大报名人数");
        } else {
            // 需要补机器人，即使是0
            var delta = maxParticipants - actualRegistrationCount;
            if (delta == 0) {
                log.info("RegistrationTaskHandler: 不需要补机器人");
            } else {
                // 不为0 且机器人模式为启用 初始化机器人实例
                var tournamentRobotInstances = tournamentRobotDomainService.initRobotInstances(eventGameId, delta);

                var tournamentParticipationRecords =  buildTournamentParticipationRecords(eventGameId, tournamentRobotInstances);

                // 补机器人参赛记录
                tournamentParticipationRecordService.bulkCreateAndFlushRedisCache(eventGameId, maxParticipants, tournamentParticipationRecords);
                log.info("1.RegistrationTaskHandler: 补机器人参赛记录");
            }
            // 初始化分组
            tournamentGroupRecordDomainService.initializeGroups(eventGameId);
            log.info("2.RegistrationTaskHandler: 初始化分组");
        }

    }

    private List<TournamentParticipationRecord> buildTournamentParticipationRecords(BigInteger eventGameId, List<TournamentRobotInstance> tournamentRobotInstances) {
        return CollectionUtils.transformToList(tournamentRobotInstances, tournamentRobotInstance -> {
            var tournamentParticipationRecord = new TournamentParticipationRecord();
            tournamentParticipationRecord.setParticipantId(tournamentRobotInstance.getId());
            tournamentParticipationRecord.setParticipantType(TournamentParticipantType.ROBOT);
            tournamentParticipationRecord.setRegistrationTime(Instant.now());
            tournamentParticipationRecord.setEventGameId(eventGameId);
            tournamentParticipationRecord.setStatus(TournamentParticipantStatus.REGISTERED);
            tournamentParticipationRecord.setTotalScore(BigDecimal.ZERO);
            return tournamentParticipationRecord;
        });
    }

}
