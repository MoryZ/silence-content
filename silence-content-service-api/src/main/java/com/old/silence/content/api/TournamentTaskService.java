package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.old.silence.content.api.dto.TournamentTaskQuery;
import com.old.silence.content.api.vo.TournamentTaskView;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;

/**
 * TournamentTask服务接口（内部调度用）
 */
interface TournamentTaskService {

    /**
     * 分页查询任务列表（管理端）
     */
    @GetMapping(value = "/tournament-tasks", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap TournamentTaskQuery query,
                      Pageable pageable,
                      @ProjectedPayloadType(TournamentTaskView.class) Class<T> projectionType);

    /**
     * 初始化赛事结算任务链（幂等）
     *
    * @param tournamentId 赛事配置ID（tournament_config.id）
     * @return 新创建任务数量
     */
    @PostMapping(value = "/tournament-tasks/init")
    int initSettlementTasks(@RequestParam("tournamentId") BigInteger tournamentId);

    /**
     * 手动重试终态失败任务
     *
     * @param taskId 任务ID
     */
    @PostMapping(value = "/tournament-tasks/{taskId}/retry")
    void retryTerminalFailedTask(@PathVariable("taskId") BigInteger taskId);

    /**
     * 处理报名截止后的分组与任务初始化（由BP触发）
     *
     * @return 本次处理的赛事数量
     */
    @PostMapping(value = "/tournament-tasks/registration-end/handle")
    int handleRegistrationEnd();

    /**
     * 处理阶段结束后的任务补偿（由BP触发）
     *
     * @return 本次处理的赛事数量
     */
    @PostMapping(value = "/tournament-tasks/stage-end/handle")
    int handleStageEnd();

    /**
     * 调度并执行待处理任务
     *
     * @return 本次调度的任务数量
     */
    @PostMapping(value = "/tournament-tasks/dispatch")
    int dispatchPendingTasks();

    /**
     * 查询赛事是否存在未完成任务（PENDING/RUNNING）
     *
    * @param tournamentId 赛事配置ID（tournament_config.id）
     * @return true=存在未完成任务
     */
    @GetMapping(value = "/tournament-tasks/unfinished")
    boolean hasUnfinishedTasks(@RequestParam("tournamentId") BigInteger tournamentId);
}
