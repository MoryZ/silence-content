package com.old.silence.content.console.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.nacos.api.model.v2.Result;
import com.old.silence.content.console.api.assembler.TournamentConfigDomainCommandMapper;
import com.old.silence.content.console.api.validation.TournamentConfigTimeValidator;
import com.old.silence.content.console.dto.TournamentConfigConsoleCommand;
import com.old.silence.content.console.service.TournamentConfigConsoleService;
import com.old.silence.content.console.vo.TournamentConfigDetailConsoleVO;
import com.old.silence.validation.group.CreationValidation;
import com.old.silence.validation.group.UpdateValidation;

import java.math.BigInteger;

/**
 * @author EX-ZHANGMENGWEI001
 */

@Validated
@RestController
@RequestMapping("/api/v1")
public class TournamentConfigController {

    private final TournamentConfigConsoleService tournamentConfigConsoleService;
    private final TournamentConfigTimeValidator tournamentConfigTimeValidator;
    private final TournamentConfigDomainCommandMapper tournamentConfigDomainCommandMapper;

    public TournamentConfigController(TournamentConfigConsoleService tournamentConfigConsoleService,
                                      TournamentConfigTimeValidator tournamentConfigTimeValidator,
                                      TournamentConfigDomainCommandMapper tournamentConfigDomainCommandMapper) {
        this.tournamentConfigConsoleService = tournamentConfigConsoleService;
        this.tournamentConfigTimeValidator = tournamentConfigTimeValidator;
        this.tournamentConfigDomainCommandMapper = tournamentConfigDomainCommandMapper;
    }

    /**
     * 详情
     */
    @GetMapping("/tournaments")
    public Result<TournamentConfigDetailConsoleVO> findByEventId(@RequestParam BigInteger eventId) {
        TournamentConfigDetailConsoleVO result = tournamentConfigConsoleService.findByEventId(eventId);
        return Result.success(result);
    }

    /**
     * 新增
     */
    @PostMapping("/tournaments")
    public Result<BigInteger> create(@RequestBody @Validated(CreationValidation.class) TournamentConfigConsoleCommand command) {
        tournamentConfigTimeValidator.checkTournamentConfig(command);
        var tournamentConfigDomainCommand = tournamentConfigDomainCommandMapper.convert(command);
        return Result.success(tournamentConfigConsoleService.create(tournamentConfigDomainCommand));
    }

    /**
     * 编辑
     */
    @PutMapping("/tournaments")
    public Result<Integer> update(@RequestBody @Validated(UpdateValidation.class) TournamentConfigConsoleCommand command) {
        // tournamentConfigTimeValidator.checkTournamentConfig(command);
        var tournamentConfigDomainCommand = tournamentConfigDomainCommandMapper.convert(command);
        return Result.success(tournamentConfigConsoleService.update(tournamentConfigDomainCommand));
    }


}
