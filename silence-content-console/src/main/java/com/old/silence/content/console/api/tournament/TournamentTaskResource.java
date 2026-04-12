package com.old.silence.content.console.api.tournament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.TournamentTaskClient;
import com.old.silence.content.console.api.assembler.TournamentTaskQueryMapper;
import com.old.silence.content.console.dto.TournamentTaskConsoleQuery;
import com.old.silence.content.console.vo.TournamentTaskConsoleView;

import java.math.BigInteger;

/**
 * 赛事任务管理（console）
 */
@RestController
@RequestMapping("/api/v1")
public class TournamentTaskResource {

    private final TournamentTaskClient tournamentTaskClient;

    private final TournamentTaskQueryMapper tournamentTaskQueryMapper;

    public TournamentTaskResource(TournamentTaskClient tournamentTaskClient,
                                  TournamentTaskQueryMapper tournamentTaskQueryMapper) {
        this.tournamentTaskClient = tournamentTaskClient;
        this.tournamentTaskQueryMapper = tournamentTaskQueryMapper;
    }

    @GetMapping(value = "/tournamentTasks", params = {"pageNo", "pageSize"})
    public Page<TournamentTaskConsoleView> query(TournamentTaskConsoleQuery query, Pageable pageable) {
        return tournamentTaskClient.query(tournamentTaskQueryMapper.convert(query), pageable, TournamentTaskConsoleView.class);
    }

    @PostMapping(value = "/tournamentTasks/init")
    public int initSettlementTasks(@RequestParam("tournamentId") BigInteger tournamentId) {
        return tournamentTaskClient.initSettlementTasks(tournamentId);
    }

    @PostMapping(value = "/tournamentTasks/{taskId}/retry")
    public void retryTerminalFailedTask(@PathVariable("taskId") BigInteger taskId) {
        tournamentTaskClient.retryTerminalFailedTask(taskId);
    }
}