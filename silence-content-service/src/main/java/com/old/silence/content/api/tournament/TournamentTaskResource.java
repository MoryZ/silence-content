package com.old.silence.content.api.tournament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.tournament.TournamentTaskMapper;
import com.old.silence.content.api.tournament.tournament.dto.TournamentTaskDomainCommand;
import com.old.silence.content.api.tournament.tournament.dto.TournamentTaskQuery;
import com.old.silence.content.api.tournament.tournament.service.TournamentTaskService;
import com.old.silence.content.domain.model.TournamentTask;
import com.old.silence.content.domain.repository.tournament.TournamentTaskRepository;
import com.old.silence.content.domain.service.tournament.task.TaskDispatcherService;
import com.old.silence.content.domain.service.tournament.task.TournamentTaskManageService;
import com.old.silence.content.infrastructure.persistence.tournament.dao.TournamentTaskDao;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * 赛事任务调度控制器（内部调度用，由 bp 模块 Job 定时触发）
 *
 * @author EX-ZHANGMENGWEI001
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class TournamentTaskResource implements TournamentTaskService {

    private final TaskDispatcherService taskDispatcherService;
    private final TournamentTaskMapper tournamentTaskMapper;
    private final TournamentTaskManageService tournamentTaskManageService;

    private final TournamentTaskRepository tournamentTaskRepository;

    private final TournamentTaskDao tournamentTaskDao;

    public TournamentTaskResource(TaskDispatcherService taskDispatcherService,
                                  TournamentTaskMapper tournamentTaskMapper,
                                  TournamentTaskManageService tournamentTaskManageService,
            TournamentTaskRepository tournamentTaskRepository, TournamentTaskDao tournamentTaskDao) {
        this.taskDispatcherService = taskDispatcherService;
        this.tournamentTaskMapper = tournamentTaskMapper;
        this.tournamentTaskManageService = tournamentTaskManageService;
        this.tournamentTaskRepository = tournamentTaskRepository;
        this.tournamentTaskDao = tournamentTaskDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return tournamentTaskRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(TournamentTaskQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, TournamentTask.class);
        return tournamentTaskRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public void initSettlementTasks(BigInteger tournamentId) {
        tournamentTaskManageService.initSettlementTasks(tournamentId);
    }

    @Override
    public void runTask(BigInteger id) {
        taskDispatcherService.processSingleTask(id);
    }

    @Override
    public void create(TournamentTaskDomainCommand command) {
        var tournamentTask = tournamentTaskMapper.convert(command);
        tournamentTaskManageService.create(tournamentTask);
    }

    @Override
    public void update(BigInteger id, TournamentTaskDomainCommand command) {
        var tournamentTask = tournamentTaskMapper.convert(command);
        tournamentTask.setId(id);
        validateModifyingResult(tournamentTaskRepository.update(tournamentTask));

    }

    @Override
    public void retryTerminalFailedTask(BigInteger id) {
        taskDispatcherService.retryTerminalFailedTask(id);
    }

    @Override
    public Integer delete(BigInteger id) {
        return tournamentTaskDao.deleteById(id);
    }
}
