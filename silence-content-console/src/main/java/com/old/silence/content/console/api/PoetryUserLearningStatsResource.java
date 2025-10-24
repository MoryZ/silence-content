package com.old.silence.content.console.api;

import java.math.BigInteger;

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
import com.old.silence.content.api.PoetryUserLearningStatsClient;
import com.old.silence.content.console.api.assembler.PoetryUserLearningStatsCommandMapper;
import com.old.silence.content.console.api.assembler.PoetryUserLearningStatsQueryMapper;
import com.old.silence.content.console.dto.PoetryUserLearningStatsConsoleCommand;
import com.old.silence.content.console.dto.PoetryUserLearningStatsConsoleQuery;
import com.old.silence.content.console.vo.PoetryUserLearningStatsConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;

/**
 * PoetryUserLearningStats资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserLearningStatsResource {
    private final PoetryUserLearningStatsClient poetryUserLearningStatsClient;
    private final PoetryUserLearningStatsCommandMapper poetryUserLearningStatsCommandMapper;
    private final PoetryUserLearningStatsQueryMapper poetryUserLearningStatsQueryMapper;

    public PoetryUserLearningStatsResource(PoetryUserLearningStatsClient poetryUserLearningStatsClient,
                                           PoetryUserLearningStatsCommandMapper poetryUserLearningStatsCommandMapper,
                                           PoetryUserLearningStatsQueryMapper poetryUserLearningStatsQueryMapper) {
        this.poetryUserLearningStatsClient = poetryUserLearningStatsClient;
        this.poetryUserLearningStatsCommandMapper = poetryUserLearningStatsCommandMapper;
        this.poetryUserLearningStatsQueryMapper = poetryUserLearningStatsQueryMapper;
    }

    @GetMapping("/poetryUserLearningStats/{id}")
    public PoetryUserLearningStatsConsoleView findById(@PathVariable BigInteger id) {
        return poetryUserLearningStatsClient.findById(id, PoetryUserLearningStatsConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/poetryUserLearningStats", params = {"pageNo", "pageSize"})
    public Page<PoetryUserLearningStatsConsoleView> query(PoetryUserLearningStatsConsoleQuery query, Pageable pageable) {
        var poetryUserLearningStatsQuery = poetryUserLearningStatsQueryMapper.convert(query);
        return poetryUserLearningStatsClient.query(poetryUserLearningStatsQuery, pageable, PoetryUserLearningStatsConsoleView.class);
    }

    @PostMapping(value = "/poetryUserLearningStats")
    public String create(@RequestBody PoetryUserLearningStatsConsoleCommand command) {
        var poetryUserLearningStatsCommand = poetryUserLearningStatsCommandMapper.convert(command);
        return String.valueOf(poetryUserLearningStatsClient.create(poetryUserLearningStatsCommand));
    }

    @PutMapping("/poetryUserLearningStats/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody PoetryUserLearningStatsConsoleCommand command) {
        var poetryUserLearningStatsCommand = poetryUserLearningStatsCommandMapper.convert(command);
        poetryUserLearningStatsClient.update(id, poetryUserLearningStatsCommand);
    }

    @DeleteMapping("/poetryUserLearningStats/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        poetryUserLearningStatsClient.deleteById(id);
    }
}