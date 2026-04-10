package com.old.silence.content.domain.service.tournament.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Service;
import com.old.silence.content.domain.enums.tournament.TaskStatusEnum;
import com.old.silence.content.domain.enums.tournament.TaskTypeEnum;
import com.old.silence.content.domain.model.tournament.TournamentTask;
import com.old.silence.content.domain.repository.tournament.TournamentTaskRepository;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 赛事任务调度分发服务
 *
 * @author moryzang
 */
@Service
public class TaskDispatcherService {

    private static final Logger log = LoggerFactory.getLogger(TaskDispatcherService.class);

    private static final int BATCH_SIZE = 100;

    private static final Map<TaskTypeEnum, Integer> TASK_PRIORITY = Map.of(
            TaskTypeEnum.STAGE_SETTLE, 1,
            TaskTypeEnum.SEGMENT_SETTLE, 2,
            TaskTypeEnum.CYCLE_SETTLE, 3
    );

    private final TournamentTaskRepository tournamentTaskRepository;

    private final TournamentTaskHandlerFactory taskHandlerFactory;

    public TaskDispatcherService(TournamentTaskRepository tournamentTaskRepository,
                                 TournamentTaskHandlerFactory taskHandlerFactory) {
        this.tournamentTaskRepository = tournamentTaskRepository;
        this.taskHandlerFactory = taskHandlerFactory;
    }

    /**
     * 调度并执行待处理任务
     *
     * @return 本次扫描的任务数量
     */
    public int dispatchPendingTasks() {
        Criteria pendingCriteria = Criteria.where("trigger_time").lessThanOrEquals(Instant.now())
                .and("status").is(TaskStatusEnum.PENDING);

        List<TournamentTask> pendingTasks = tournamentTaskRepository
                .findByCriteria(pendingCriteria, PageRequest.of(0, BATCH_SIZE), TournamentTask.class)
                .getContent()
                .stream()
                .sorted(taskExecutionComparator())
                .toList();

        for (TournamentTask task : pendingTasks) {
            processSingleTask(task);
        }

        return pendingTasks.size();
    }

    private void processSingleTask(TournamentTask task) {
        try {
            updateStatus(task, TaskStatusEnum.RUNNING);
            taskHandlerFactory.getHandler(task.getTaskType()).execute(task);
            updateStatus(task, TaskStatusEnum.SUCCESS);
        } catch (Exception ex) {
            markFailed(task, ex);
        }
    }

    private void updateStatus(TournamentTask task, TaskStatusEnum status) {
        task.setStatus(status);
        tournamentTaskRepository.update(task);
    }

    private void markFailed(TournamentTask task, Exception ex) {
        task.setStatus(TaskStatusEnum.FAILED);
        task.setRetryCount(task.getRetryCount() == null ? 1 : task.getRetryCount() + 1);
        tournamentTaskRepository.update(task);
        log.error("Tournament task execution failed, taskId={}, tournamentId={}, taskType={}, periodNo={}",
                task.getTaskId(), task.getTournamentId(), task.getTaskType(), task.getPeriodNo(), ex);
    }

    private Comparator<TournamentTask> taskExecutionComparator() {
        return Comparator
                .comparing(TournamentTask::getTriggerTime)
                .thenComparing(task -> TASK_PRIORITY.getOrDefault(task.getTaskType(), Integer.MAX_VALUE));
    }
}
