package com.old.silence.content.domain.service.tournament.task.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;
import com.old.silence.content.domain.model.TournamentTask;
import com.old.silence.content.domain.service.tournament.TournamentConfigCacheDomainService;
import com.old.silence.content.domain.service.tournament.TournamentIssueRewardDomainService;
import com.old.silence.content.domain.service.tournament.task.TournamentTaskHandlerStrategy;


/**
 * 发放奖励任务处理器
 *
 * @author EX-ZHANGMENGWEI001
 */
@Component
public class IssueRewardTaskHandlerStrategy implements TournamentTaskHandlerStrategy {

    private static final Logger log = LoggerFactory.getLogger(IssueRewardTaskHandlerStrategy.class);

    private final TournamentIssueRewardDomainService tournamentIssueRewardDomainService;
    private final TournamentConfigCacheDomainService tournamentConfigCacheDomainService;

    public IssueRewardTaskHandlerStrategy(TournamentIssueRewardDomainService tournamentIssueRewardDomainService,
                                          TournamentConfigCacheDomainService tournamentConfigCacheDomainService) {
        this.tournamentIssueRewardDomainService = tournamentIssueRewardDomainService;
        this.tournamentConfigCacheDomainService = tournamentConfigCacheDomainService;
    }

    @Override
    public TournamentTaskType getTaskType() {
        return TournamentTaskType.ISSUE_REWARD;
    }

    @Override
    public void execute(TournamentTask tournamentTask) {

        log.info("IssueRewardTaskHandler done, id={}, tournamentId={}, cycleNo={}",
            tournamentTask.getId(), tournamentTask.getTournamentId(), tournamentTask.getCycleNo());
    }

}
