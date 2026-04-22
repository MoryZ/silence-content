package com.old.silence.bp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.old.silence.bp.util.TournamentTimeCalculationUtils;
import com.old.silence.bp.vo.TournamentConfigBpView;
import com.old.silence.bp.vo.TournamentTaskBpView;
import com.old.silence.content.api.TournamentConfigDomainClient;
import com.old.silence.content.api.TournamentGroupRecordClient;
import com.old.silence.content.api.TournamentScoreRecordClient;
import com.old.silence.content.api.tournament.tournament.dto.support.CycleStageConfig;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.json.JacksonMapper;


import java.time.Instant;


/**
 * 周期结算任务处理器
 *
 * @author EX-ZHANGMENGWEI001
 */
@Component
public class CycleSettleTaskService {

    private static final Logger log = LoggerFactory.getLogger(CycleSettleTaskService.class);

    private final TournamentConfigDomainClient tournamentConfigDomainClient;
    private final TournamentScoreRecordClient tournamentScoreRecordClient;
    private final TournamentGroupRecordClient tournamentGroupRecordClient;
    private final TournamentRankService tournamentRankService;

    public CycleSettleTaskService(TournamentConfigDomainClient tournamentConfigDomainClient,
                                  TournamentScoreRecordClient tournamentScoreRecordClient,
                                  TournamentGroupRecordClient tournamentGroupRecordClient,
                                  TournamentRankService tournamentRankService) {
        this.tournamentConfigDomainClient = tournamentConfigDomainClient;
        this.tournamentScoreRecordClient = tournamentScoreRecordClient;
        this.tournamentGroupRecordClient = tournamentGroupRecordClient;
        this.tournamentRankService = tournamentRankService;
    }

    public void execute(TournamentTaskBpView tournamentTask) {
        var eventGameId = tournamentTask.getEventGameId();
        var tournamentConfig = tournamentConfigDomainClient.findByEventGameId(eventGameId, TournamentConfigBpView.class)
                .orElseThrow(ResourceNotFoundException::new);
        var cycleStageConfig = JacksonMapper.getSharedInstance().unwrap().convertValue(tournamentConfig.getAttributes(), CycleStageConfig.class);
        // 判断是否最后一个周期
        if (TournamentTimeCalculationUtils.isLastCycle(Instant.now(), tournamentConfig.getTournamentEndTime())) {
            // 1.查找所有得分记录(赛事累计)
            tournamentScoreRecordClient.calculateTournamentScoreByEventGameId(tournamentTask.getEventGameId());

            tournamentRankService.globalRank(eventGameId);
        // 2.计算全国排名 创建全国排行榜， 需用机器人补足排行榜，真实参数人数≥10人时，机器人不能进入前10名；且真实用户加入排名后，需倒数排名分数把机器人挤掉
            // 2.排行榜记录的只是topK,要生成赛事维度的总分，供排行榜使用
            // 2.1.计算全国排名 创建全国排行榜， 需用机器人补足排行榜，真实参数人数≥10人时，机器人不能进入前10名；且真实用户加入排名后，需倒数排名分数把机器人挤掉

        } else {
            // 3.重新分组
            tournamentGroupRecordClient.regrouping(eventGameId, tournamentTask.getCycleNo());
        }

        log.info("CycleSettleTaskHandler done, id={}, tournamentId={}, cycleNo={}",
            tournamentTask.getId(), tournamentTask.getTournamentId(), tournamentTask.getCycleNo());
    }


}
