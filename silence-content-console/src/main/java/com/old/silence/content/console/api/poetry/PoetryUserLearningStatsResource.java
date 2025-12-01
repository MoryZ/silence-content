package com.old.silence.content.console.api.poetry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryUserLearningStatsClient;
import com.old.silence.content.console.api.assembler.PoetryUserLearningStatsQueryMapper;
import com.old.silence.content.console.dto.PoetryUserLearningStatsConsoleQuery;
import com.old.silence.content.console.vo.PoetryUserLearningStatsConsoleView;

/**
 * PoetryUserLearningStats资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserLearningStatsResource {
    private final PoetryUserLearningStatsClient poetryUserLearningStatsClient;
    private final PoetryUserLearningStatsQueryMapper poetryUserLearningStatsQueryMapper;

    public PoetryUserLearningStatsResource(PoetryUserLearningStatsClient poetryUserLearningStatsClient,
                                           PoetryUserLearningStatsQueryMapper poetryUserLearningStatsQueryMapper) {
        this.poetryUserLearningStatsClient = poetryUserLearningStatsClient;
        this.poetryUserLearningStatsQueryMapper = poetryUserLearningStatsQueryMapper;
    }

    @GetMapping(value = "/poetryUserLearningStats", params = {"pageNo", "pageSize"})
    public Page<PoetryUserLearningStatsConsoleView> query(PoetryUserLearningStatsConsoleQuery query, Pageable pageable) {
        var poetryUserLearningStatsQuery = poetryUserLearningStatsQueryMapper.convert(query);
        return poetryUserLearningStatsClient.query(poetryUserLearningStatsQuery, pageable, PoetryUserLearningStatsConsoleView.class);
    }

}