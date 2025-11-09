package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryUserLearningRecordClient;
import com.old.silence.content.console.api.assembler.PoetryUserLearningRecordQueryMapper;
import com.old.silence.content.console.dto.PoetryUserLearningRecordConsoleQuery;
import com.old.silence.content.console.vo.PoetryUserLearningRecordConsoleView;

/**
 * PoetryUserLearningRecord资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserLearningRecordResource {
    private final PoetryUserLearningRecordClient poetryUserLearningRecordClient;
    private final PoetryUserLearningRecordQueryMapper poetryUserLearningRecordQueryMapper;

    public PoetryUserLearningRecordResource(PoetryUserLearningRecordClient poetryUserLearningRecordClient,
                                            PoetryUserLearningRecordQueryMapper poetryUserLearningRecordQueryMapper) {
        this.poetryUserLearningRecordClient = poetryUserLearningRecordClient;
        this.poetryUserLearningRecordQueryMapper = poetryUserLearningRecordQueryMapper;
    }

    @GetMapping(value = "/poetryUserLearningRecords", params = {"pageNo", "pageSize"})
    public Page<PoetryUserLearningRecordConsoleView> query(PoetryUserLearningRecordConsoleQuery query, Pageable pageable) {
        var poetryUserLearningRecordQuery = poetryUserLearningRecordQueryMapper.convert(query);
        return poetryUserLearningRecordClient.query(poetryUserLearningRecordQuery, pageable, PoetryUserLearningRecordConsoleView.class);
    }

}