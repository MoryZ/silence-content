package com.old.silence.content.domain.service.tournament.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Service;
import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;
import com.old.silence.content.domain.service.tournament.task.event.TournamentRewardDispatchRequestedEvent;
import com.old.silence.content.domain.model.tournament.TournamentGroupRecord;
import com.old.silence.content.domain.model.tournament.TournamentTask;
import com.old.silence.content.domain.repository.tournament.TournamentGroupRecordRepository;
import com.old.silence.content.domain.repository.tournament.TournamentTaskRepository;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * 赛事任务调度分发服务
 *
 * @author moryzang
 */
@Service
public class TaskDispatcherService {

    private static final Logger log = LoggerFactory.getLogger(TaskDispatcherService.class);

    private static final int BATCH_SIZE = 100;

    private static final int LOOKUP_SIZE = 1;

    private static final int MAX_RETRY_COUNT = 3;

    private static final Map<TournamentTaskType, Integer> TASK_PRIORITY = Map.of(
            TournamentTaskType.STAGE_SETTLE, 1,
            TournamentTaskType.SEGMENT_SETTLE, 2,
            TournamentTaskType.CYCLE_SETTLE, 3
    );

    private final TournamentTaskRepository tournamentTaskRepository;

    private final TournamentGroupRecordRepository tournamentGroupRecordRepository;

    private final TournamentTaskHandlerFactory taskHandlerFactory;

    private final ApplicationEventPublisher applicationEventPublisher;

    public TaskDispatcherService(TournamentTaskRepository tournamentTaskRepository,
                                 TournamentGroupRecordRepository tournamentGroupRecordRepository,
                                 TournamentTaskHandlerFactory taskHandlerFactory,
                                 ApplicationEventPublisher applicationEventPublisher) {
        this.tournamentTaskRepository = tournamentTaskRepository;
        this.tournamentGroupRecordRepository = tournamentGroupRecordRepository;
        this.taskHandlerFactory = taskHandlerFactory;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 调度并执行待处理任务
     *
     * @return 本次扫描的任务数量
     */
    public int dispatchPendingTasks() {
        Instant now = Instant.now();
        Criteria pendingCriteria = Criteria.where("trigger_time").lessThanOrEquals(Instant.now())
                .and("status").is(TournamentTaskStatus.PENDING);

        Criteria failedCriteria = Criteria.where("trigger_time").lessThanOrEquals(now)
            .and("status").is(TournamentTaskStatus.FAILED);

        List<TournamentTask> pendingTasks = tournamentTaskRepository
                .findByCriteria(pendingCriteria, PageRequest.of(0, BATCH_SIZE), TournamentTask.class)
                .getContent()
                .stream()
            .toList();

        List<TournamentTask> failedTasks = tournamentTaskRepository
            .findByCriteria(failedCriteria, PageRequest.of(0, BATCH_SIZE), TournamentTask.class)
            .getContent()
            .stream()
            .filter(task -> (task.getRetryCount() == null ? 0 : task.getRetryCount()) < MAX_RETRY_COUNT)
            .toList();

        List<TournamentTask> pendingAndFailedTasks = java.util.stream.Stream.concat(pendingTasks.stream(), failedTasks.stream())
            .sorted(taskExecutionComparator())
            .toList();

        for (TournamentTask task : pendingAndFailedTasks) {
            processSingleTask(task);
        }

        return pendingAndFailedTasks.size();
    }

    private void processSingleTask(TournamentTask task) {
        try {
            ensureRunTraceId(task);
            if (!isDependencySatisfied(task)) {
                log.info("Tournament task dependency not satisfied yet, id={}, runTraceId={}, tournamentId={}, taskType={}, stageNo={}, segmentNo={}, cycleNo={}",
                    task.getId(), task.getRunTraceId(), task.getTournamentId(), task.getTaskType(),
                    task.getStageNo(), task.getSegmentNo(), task.getCycleNo());
                return;
            }
            updateStatus(task, TournamentTaskStatus.RUNNING);
            taskHandlerFactory.getHandler(task.getTaskType()).execute(task);
            updateStatus(task, TournamentTaskStatus.SUCCESS);
            appendNextTaskOnSuccess(task);
            emitRewardDispatchEventIfReady(task);
        } catch (Exception ex) {
            markFailed(task, ex);
        }
    }

    private void updateStatus(TournamentTask task, TournamentTaskStatus status) {
        task.setStatus(status);
        tournamentTaskRepository.update(task);
    }

    private void markFailed(TournamentTask task, Exception ex) {
        int nextRetryCount = task.getRetryCount() == null ? 1 : task.getRetryCount() + 1;
        task.setRetryCount(nextRetryCount);
        if (nextRetryCount >= MAX_RETRY_COUNT) {
            task.setStatus(TournamentTaskStatus.TERMINAL_FAILED);
        } else {
            task.setStatus(TournamentTaskStatus.FAILED);
        }
        tournamentTaskRepository.update(task);
        log.error("Tournament task execution failed, id={}, runTraceId={}, tournamentId={}, taskType={}, stageNo={}, segmentNo={}, cycleNo={}, retryCount={}, status={}",
            task.getId(), task.getRunTraceId(), task.getTournamentId(), task.getTaskType(),
            task.getStageNo(), task.getSegmentNo(), task.getCycleNo(), task.getRetryCount(), task.getStatus(), ex);
    }

    private Comparator<TournamentTask> taskExecutionComparator() {
        return Comparator
                .comparing(TournamentTask::getTriggerTime)
                .thenComparing(task -> TASK_PRIORITY.getOrDefault(task.getTaskType(), Integer.MAX_VALUE));
    }

        /**
         * 是否还存在未完成任务（PENDING/RUNNING）
         *
         * @param tournamentId 赛事ID
         * @return true=有未完成任务
         */
        public boolean hasUnfinishedTasks(BigInteger tournamentId) {
        return hasTaskInStatus(tournamentId, TournamentTaskStatus.PENDING)
            || hasTaskInStatus(tournamentId, TournamentTaskStatus.RUNNING)
            || hasTaskInStatus(tournamentId, TournamentTaskStatus.FAILED);
        }

        private boolean isDependencySatisfied(TournamentTask task) {
            if (task.getDependsOnTaskId() != null) {
                return isDependsOnTaskSatisfied(task);
            }
        return switch (task.getTaskType()) {
            case STAGE_SETTLE -> isRegistrationPrepared(task.getEventGameId());
            case SEGMENT_SETTLE -> isPreTaskTypeCompleted(task, TournamentTaskType.STAGE_SETTLE);
            case CYCLE_SETTLE -> isPreTaskTypeCompleted(task, TournamentTaskType.SEGMENT_SETTLE);
        };
        }

        private boolean isDependsOnTaskSatisfied(TournamentTask task) {
            TournamentTask dependencyTask = tournamentTaskRepository
                    .findById(task.getDependsOnTaskId(), TournamentTask.class)
                    .orElse(null);
            if (dependencyTask == null) {
                return false;
            }
            if (!task.getTournamentId().equals(dependencyTask.getTournamentId())) {
                return false;
            }
            TournamentTaskStatus expectedStatus = task.getDependsOnStatus() == null
                    ? TournamentTaskStatus.SUCCESS
                    : task.getDependsOnStatus();
            return expectedStatus.equals(dependencyTask.getStatus());
        }

        private boolean isRegistrationPrepared(BigInteger tournamentId) {
        Criteria criteria = Criteria.where("event_game_id").is(tournamentId);
        return !tournamentGroupRecordRepository
                    .findByCriteria(criteria, PageRequest.of(0, LOOKUP_SIZE), TournamentGroupRecord.class)
            .isEmpty();
        }

        private boolean isPreTaskTypeCompleted(TournamentTask task, TournamentTaskType preTaskType) {
            Criteria unfinishedCriteria = addTaskScopeCriteria(
                Criteria.where("tournament_id").is(task.getTournamentId())
                    .and("task_type").is(preTaskType),
                task,
                preTaskType)
                .and("status").is(TournamentTaskStatus.PENDING);
        boolean hasPending = !tournamentTaskRepository
            .findByCriteria(unfinishedCriteria, PageRequest.of(0, LOOKUP_SIZE), TournamentTask.class)
            .isEmpty();
        if (hasPending) {
            return false;
        }

        Criteria runningCriteria = addTaskScopeCriteria(
            Criteria.where("tournament_id").is(task.getTournamentId())
                .and("task_type").is(preTaskType),
            task,
            preTaskType)
            .and("status").is(TournamentTaskStatus.RUNNING);
        boolean hasRunning = !tournamentTaskRepository
            .findByCriteria(runningCriteria, PageRequest.of(0, LOOKUP_SIZE), TournamentTask.class)
            .isEmpty();
        if (hasRunning) {
            return false;
        }

        Criteria successCriteria = addTaskScopeCriteria(
            Criteria.where("tournament_id").is(task.getTournamentId())
                .and("task_type").is(preTaskType),
            task,
            preTaskType)
            .and("status").is(TournamentTaskStatus.SUCCESS);
        return !tournamentTaskRepository
            .findByCriteria(successCriteria, PageRequest.of(0, LOOKUP_SIZE), TournamentTask.class)
            .isEmpty();
        }

        private Criteria addTaskScopeCriteria(Criteria baseCriteria, TournamentTask task, TournamentTaskType taskType) {
        return switch (taskType) {
            case STAGE_SETTLE -> baseCriteria
                .and("stage_no").is(task.getStageNo())
                .and("segment_no").is(task.getSegmentNo())
                .and("cycle_no").is(task.getCycleNo());
            case SEGMENT_SETTLE -> baseCriteria
                .and("segment_no").is(task.getSegmentNo())
                .and("cycle_no").is(task.getCycleNo());
            case CYCLE_SETTLE -> baseCriteria
                .and("cycle_no").is(task.getCycleNo());
        };
        }

        private boolean hasTaskInStatus(BigInteger tournamentId, TournamentTaskStatus status) {
        Criteria criteria = Criteria.where("tournament_id").is(tournamentId)
            .and("status").is(status);
        return !tournamentTaskRepository
            .findByCriteria(criteria, PageRequest.of(0, LOOKUP_SIZE), TournamentTask.class)
            .isEmpty();
        }

        private void ensureRunTraceId(TournamentTask task) {
            if (task.getRunTraceId() != null && !task.getRunTraceId().isBlank()) {
                return;
            }

            String traceId = null;
            if (task.getDependsOnTaskId() != null) {
                TournamentTask dependencyTask = tournamentTaskRepository
                        .findById(task.getDependsOnTaskId(), TournamentTask.class)
                        .orElse(null);
                if (dependencyTask != null) {
                    traceId = dependencyTask.getRunTraceId();
                }
            }

            if (traceId == null || traceId.isBlank()) {
                traceId = UUID.randomUUID().toString().replace("-", "");
            }
            task.setRunTraceId(traceId);
            tournamentTaskRepository.update(task);
        }

        private void appendNextTaskOnSuccess(TournamentTask finishedTask) {
            TournamentTaskType nextTaskType = switch (finishedTask.getTaskType()) {
                case STAGE_SETTLE -> TournamentTaskType.SEGMENT_SETTLE;
                case SEGMENT_SETTLE -> TournamentTaskType.CYCLE_SETTLE;
                case CYCLE_SETTLE -> null;
            };
            if (nextTaskType == null) {
                return;
            }

            if (existsTaskWithSameScope(finishedTask, nextTaskType)) {
                return;
            }

            TournamentTask nextTask = new TournamentTask();
            nextTask.setTournamentId(finishedTask.getTournamentId());
            nextTask.setEventGameId(finishedTask.getEventGameId());
            nextTask.setTaskType(nextTaskType);
            nextTask.setStageNo(finishedTask.getStageNo());
            nextTask.setSegmentNo(finishedTask.getSegmentNo());
            nextTask.setCycleNo(finishedTask.getCycleNo());
            nextTask.setTriggerTime(Instant.now());
            nextTask.setStatus(TournamentTaskStatus.PENDING);
            nextTask.setRetryCount(0);
            nextTask.setDependsOnTaskId(finishedTask.getId());
            nextTask.setDependsOnStatus(TournamentTaskStatus.SUCCESS);
            nextTask.setRunTraceId(finishedTask.getRunTraceId());
            tournamentTaskRepository.create(nextTask);

            log.info("Appended next task after success, currentTaskId={}, nextTaskId={}, tournamentId={}, nextTaskType={}, stageNo={}, segmentNo={}, cycleNo={}, runTraceId={}",
                    finishedTask.getId(), nextTask.getId(), nextTask.getTournamentId(), nextTask.getTaskType(),
                    nextTask.getStageNo(), nextTask.getSegmentNo(), nextTask.getCycleNo(), nextTask.getRunTraceId());
        }

        private boolean existsTaskWithSameScope(TournamentTask sourceTask, TournamentTaskType taskType) {
            Criteria criteria = addTaskScopeCriteria(
                    Criteria.where("tournament_id").is(sourceTask.getTournamentId())
                            .and("task_type").is(taskType),
                    sourceTask,
                    taskType);
            Optional<TournamentTask> existing = tournamentTaskRepository
                    .findByCriteria(criteria, PageRequest.of(0, LOOKUP_SIZE), TournamentTask.class)
                    .stream()
                    .findFirst();
            return existing.isPresent();
        }

        private void emitRewardDispatchEventIfReady(TournamentTask task) {
            if (task.getTaskType() != TournamentTaskType.CYCLE_SETTLE) {
                return;
            }
            if (hasUnfinishedTasks(task.getTournamentId())) {
                return;
            }
            applicationEventPublisher.publishEvent(new TournamentRewardDispatchRequestedEvent(
                    task.getTournamentId(),
                    task.getEventGameId(),
                    task.getRunTraceId(),
                    Instant.now()));
        }
}
