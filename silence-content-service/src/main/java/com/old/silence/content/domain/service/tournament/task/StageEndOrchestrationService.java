package com.old.silence.content.domain.service.tournament.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Service;
import com.old.silence.content.api.vo.TournamentConfigView;
import com.old.silence.content.domain.repository.tournament.TournamentConfigRepository;

import java.time.Instant;
import java.util.List;

/**
 * 阶段结束编排服务（service侧）
 */
@Service
public class StageEndOrchestrationService {

    private static final Logger log = LoggerFactory.getLogger(StageEndOrchestrationService.class);

    private static final int PAGE_SIZE = 500;

    private final TournamentConfigRepository tournamentConfigRepository;

    private final TournamentTaskManageService tournamentTaskManageService;

    public StageEndOrchestrationService(TournamentConfigRepository tournamentConfigRepository,
                                        TournamentTaskManageService tournamentTaskManageService) {
        this.tournamentConfigRepository = tournamentConfigRepository;
        this.tournamentTaskManageService = tournamentTaskManageService;
    }

    public int handleStageEnd() {
        Instant now = Instant.now();
        int handled = 0;
        List<TournamentConfigView> configs = tournamentConfigRepository
                .findByCriteria(Criteria.empty(), PageRequest.of(0, PAGE_SIZE), TournamentConfigView.class)
                .getContent();

        for (TournamentConfigView config : configs) {
            if (!isStageWindowClosed(config, now)) {
                continue;
            }
            try {
                int created = tournamentTaskManageService.initSettlementTasks(config.getId());
                log.info("stage-end orchestration handled, tournamentId={}, eventGameId={}, createdStageTasks={}",
                        config.getId(), config.getEventGameId(), created);
                handled++;
            } catch (Exception ex) {
                log.error("stage-end orchestration failed, tournamentId={}, eventGameId={}",
                        config.getId(), config.getEventGameId(), ex);
            }
        }
        return handled;
    }

    private boolean isStageWindowClosed(TournamentConfigView config, Instant now) {
        return config.getTournamentEndTime() != null && now.isAfter(config.getTournamentEndTime());
    }
}