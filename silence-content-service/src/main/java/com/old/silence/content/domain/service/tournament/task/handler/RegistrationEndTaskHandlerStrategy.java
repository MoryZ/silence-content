package com.old.silence.content.domain.service.tournament.task.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;
import com.old.silence.content.domain.model.tournament.TournamentTask;
import com.old.silence.content.domain.service.tournament.task.TournamentTaskHandlerStrategy;
import com.old.silence.content.domain.service.tournament.task.TournamentTaskManageService;

/**
 * 报名结束任务处理器
 *
 * @author moryzang
 */
@Component
public class RegistrationEndTaskHandlerStrategy implements TournamentTaskHandlerStrategy {

    private static final Logger log = LoggerFactory.getLogger(RegistrationEndTaskHandlerStrategy.class);

    private final TournamentTaskManageService tournamentTaskManageService;

    public RegistrationEndTaskHandlerStrategy(TournamentTaskManageService tournamentTaskManageService) {
        this.tournamentTaskManageService = tournamentTaskManageService;
    }

    @Override
    public TournamentTaskType getTaskType() {
        return TournamentTaskType.REGISTRATION_END;
    }

    @Override
    public void execute(TournamentTask task) {
        log.info("RegistrationEndTaskHandler execute, taskId={}, tournamentId={}, eventGameId={}",
                task.getId(), task.getTournamentId(), task.getEventGameId());
        tournamentTaskManageService.createInitialStageTasks(task.getTournamentId(), task.getEventGameId(), task.getId(), task.getRunTraceId());
    }
}
