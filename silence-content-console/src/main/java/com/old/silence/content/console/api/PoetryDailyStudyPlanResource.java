package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryDailyStudyPlanClient;
import com.old.silence.content.console.api.assembler.PoetryDailyStudyPlanCommandMapper;
import com.old.silence.content.console.api.assembler.PoetryDailyStudyPlanQueryMapper;
import com.old.silence.content.console.dto.PoetryDailyStudyPlanConsoleCommand;
import com.old.silence.content.console.dto.PoetryDailyStudyPlanConsoleQuery;
import com.old.silence.content.console.vo.PoetryDailyStudyPlanConsoleView;

import java.math.BigInteger;

/**
 * PoetryDailyStudyPlan资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryDailyStudyPlanResource {
    private final PoetryDailyStudyPlanClient poetryDailyStudyPlanClient;
    private final PoetryDailyStudyPlanCommandMapper poetryDailyStudyPlanCommandMapper;
    private final PoetryDailyStudyPlanQueryMapper poetryDailyStudyPlanQueryMapper;

    public PoetryDailyStudyPlanResource(PoetryDailyStudyPlanClient poetryDailyStudyPlanClient,
                                        PoetryDailyStudyPlanCommandMapper poetryDailyStudyPlanCommandMapper,
                                        PoetryDailyStudyPlanQueryMapper poetryDailyStudyPlanQueryMapper) {
        this.poetryDailyStudyPlanClient = poetryDailyStudyPlanClient;
        this.poetryDailyStudyPlanCommandMapper = poetryDailyStudyPlanCommandMapper;
        this.poetryDailyStudyPlanQueryMapper = poetryDailyStudyPlanQueryMapper;
    }


    @GetMapping(value = "/poetryDailyStudyPlans", params = {"pageNo", "pageSize"})
    public Page<PoetryDailyStudyPlanConsoleView> query(PoetryDailyStudyPlanConsoleQuery query, Pageable pageable) {
        var poetryDailyStudyPlanQuery = poetryDailyStudyPlanQueryMapper.convert(query);
        return poetryDailyStudyPlanClient.query(poetryDailyStudyPlanQuery, pageable, PoetryDailyStudyPlanConsoleView.class);
    }

    @PostMapping(value = "/poetryDailyStudyPlans")
    public String create(@RequestBody PoetryDailyStudyPlanConsoleCommand command) {
        var poetryDailyStudyPlanCommand = poetryDailyStudyPlanCommandMapper.convert(command);
        return String.valueOf(poetryDailyStudyPlanClient.create(poetryDailyStudyPlanCommand));
    }

    @PutMapping("/poetryDailyStudyPlans/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody PoetryDailyStudyPlanConsoleCommand command) {
        var poetryDailyStudyPlanCommand = poetryDailyStudyPlanCommandMapper.convert(command);
        poetryDailyStudyPlanClient.update(id, poetryDailyStudyPlanCommand);
    }

    @DeleteMapping("/poetryDailyStudyPlans/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        poetryDailyStudyPlanClient.deleteById(id);
    }
}