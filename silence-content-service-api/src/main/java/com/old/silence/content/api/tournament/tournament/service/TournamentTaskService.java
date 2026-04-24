package com.old.silence.content.api.tournament.tournament.service;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.old.silence.content.api.tournament.tournament.dto.TournamentTaskDomainCommand;
import com.old.silence.content.api.tournament.tournament.dto.TournamentTaskQuery;
import com.old.silence.content.api.tournament.tournament.vo.TournamentTaskView;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
 * TournamentTask服务接口（内部调度用）
 */
public interface TournamentTaskService {

    @GetMapping(value = "/tournamentTasks/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id,
                      @ProjectedPayloadType(TournamentTaskView.class) Class<T> projectionType);

    @GetMapping(value = "/tournamentTasks", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap TournamentTaskQuery query, Pageable pageable,
                      @ProjectedPayloadType(TournamentTaskView.class) Class<T> projectionType);

    /**
     * 初始化赛事结算任务链（幂等,可重入）
     *
    * @param tournamentId 赛事配置ID
     */
    @PostMapping(value = "/tournamentTasks/init")
    void initSettlementTasks(@RequestParam BigInteger tournamentId);

    /**
     * 创建单条任务
     *
     * @param command 任务实体
     */
    @PostMapping(value = "/tournamentTasks")
    void create(@RequestBody @Validated TournamentTaskDomainCommand command);


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutJsonMapping(value = "/tournamentTasks/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated TournamentTaskDomainCommand command);


    /**
     * 调度并执行触发单条任务
     *
     * @param id 任务ID
     */
    @PostMapping(value = "/tournamentTasks/{id}/run")
    void runTask(@PathVariable BigInteger id);

    /**
     * 手动重试终态失败任务
     *
     * @param id 任务ID
     */
    @PostMapping(value = "/tournamentTasks/{id}/retry")
    void retryTerminalFailedTask(@PathVariable BigInteger id);

    @DeleteMapping(value = "/tournamentTasks/{id}")
    Integer delete(@PathVariable BigInteger id);

}
