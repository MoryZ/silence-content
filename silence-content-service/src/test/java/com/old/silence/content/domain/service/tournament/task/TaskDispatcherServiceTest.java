package com.old.silence.content.domain.service.tournament.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.enums.tournament.TournamentTaskStatus;
import com.old.silence.content.domain.enums.tournament.TournamentTaskType;
import com.old.silence.content.domain.model.tournament.TournamentTask;
import com.old.silence.content.domain.repository.tournament.TournamentGroupRecordRepository;
import com.old.silence.content.domain.repository.tournament.TournamentTaskRepository;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskDispatcherServiceTest {

    @Mock
    private TournamentTaskRepository tournamentTaskRepository;

    @Mock
    private TournamentGroupRecordRepository tournamentGroupRecordRepository;

    @Mock
    private TournamentTaskHandlerFactory taskHandlerFactory;

    @Mock
    private TournamentTaskHandlerStrategy handlerStrategy;

    private TaskDispatcherService taskDispatcherService;

    @BeforeEach
    void setUp() {
        taskDispatcherService = new TaskDispatcherService(
                tournamentTaskRepository,
                tournamentGroupRecordRepository,
                taskHandlerFactory);
    }

    @Test
    void shouldDispatchAndPropagateRunTraceFromDependency() {
        TournamentTask task = buildTask(BigInteger.valueOf(1001L), BigInteger.valueOf(2001L), TournamentTaskType.CYCLE_SETTLE);
        task.setDependsOnTaskId(BigInteger.valueOf(9001L));
        task.setDependsOnStatus(TournamentTaskStatus.SUCCESS);

        TournamentTask dependency = buildTask(BigInteger.valueOf(9001L), BigInteger.valueOf(2001L), TournamentTaskType.SEGMENT_SETTLE);
        dependency.setStatus(TournamentTaskStatus.SUCCESS);
        dependency.setRunTraceId("TRACE_FROM_DEPENDENCY");

        when(tournamentTaskRepository.findByCriteria(any(Criteria.class), any(PageRequest.class), eq(TournamentTask.class)))
                .thenReturn(new PageImpl<>(List.of(task)));
        when(tournamentTaskRepository.findById(eq(BigInteger.valueOf(9001L)), eq(TournamentTask.class)))
                .thenReturn(Optional.of(dependency));
        when(taskHandlerFactory.getHandler(eq(TournamentTaskType.CYCLE_SETTLE))).thenReturn(handlerStrategy);

        int dispatched = taskDispatcherService.dispatchPendingTasks();

        Assertions.assertEquals(1, dispatched);
        Assertions.assertEquals("TRACE_FROM_DEPENDENCY", task.getRunTraceId());
        Assertions.assertEquals(TournamentTaskStatus.SUCCESS, task.getStatus());
        verify(taskHandlerFactory, times(1)).getHandler(eq(TournamentTaskType.CYCLE_SETTLE));
        verify(handlerStrategy, times(1)).execute(eq(task));
        verify(tournamentTaskRepository, times(3)).update(eq(task));
    }

    @Test
    void shouldSkipExecutionWhenDependencyNotSatisfied() {
        TournamentTask task = buildTask(BigInteger.valueOf(1002L), BigInteger.valueOf(2002L), TournamentTaskType.SEGMENT_SETTLE);
        task.setRunTraceId("TRACE_KEEP");
        task.setDependsOnTaskId(BigInteger.valueOf(9002L));
        task.setDependsOnStatus(TournamentTaskStatus.SUCCESS);

        TournamentTask dependency = buildTask(BigInteger.valueOf(9002L), BigInteger.valueOf(2002L), TournamentTaskType.STAGE_SETTLE);
        dependency.setStatus(TournamentTaskStatus.FAILED);

        when(tournamentTaskRepository.findByCriteria(any(Criteria.class), any(PageRequest.class), eq(TournamentTask.class)))
                .thenReturn(new PageImpl<>(List.of(task)));
        when(tournamentTaskRepository.findById(eq(BigInteger.valueOf(9002L)), eq(TournamentTask.class)))
                .thenReturn(Optional.of(dependency));

        int dispatched = taskDispatcherService.dispatchPendingTasks();

        Assertions.assertEquals(1, dispatched);
        Assertions.assertEquals(TournamentTaskStatus.PENDING, task.getStatus());
        verify(taskHandlerFactory, never()).getHandler(any(TournamentTaskType.class));
        verify(tournamentTaskRepository, never()).update(eq(task));
    }

    @Test
    void shouldReturnTrueWhenHasUnfinishedTasks() {
        TournamentTask pendingTask = buildTask(BigInteger.valueOf(1003L), BigInteger.valueOf(2003L), TournamentTaskType.STAGE_SETTLE);
        pendingTask.setStatus(TournamentTaskStatus.PENDING);

        when(tournamentTaskRepository.findByCriteria(any(Criteria.class), any(PageRequest.class), eq(TournamentTask.class)))
                .thenReturn(new PageImpl<>(List.of(pendingTask)));

        boolean result = taskDispatcherService.hasUnfinishedTasks(BigInteger.valueOf(2003L));

        Assertions.assertTrue(result);
    }

    private TournamentTask buildTask(BigInteger id, BigInteger tournamentId, TournamentTaskType taskType) {
        TournamentTask task = new TournamentTask();
        task.setId(id);
        task.setTournamentId(tournamentId);
        task.setTaskType(taskType);
        task.setStatus(TournamentTaskStatus.PENDING);
        task.setRetryCount(0);
        task.setStageNo(1);
        task.setSegmentNo(1);
        task.setCycleNo(1);
        task.setTriggerTime(Instant.now().minusSeconds(60));
        return task;
    }
}
