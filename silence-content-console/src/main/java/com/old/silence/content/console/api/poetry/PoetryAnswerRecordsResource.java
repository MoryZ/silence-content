package com.old.silence.content.console.api.poetry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryAnswerRecordsClient;
import com.old.silence.content.console.api.assembler.PoetryAnswerRecordsQueryMapper;
import com.old.silence.content.console.dto.PoetryAnswerRecordsConsoleQuery;
import com.old.silence.content.console.vo.PoetryAnswerRecordsConsoleView;

/**
 * PoetryAnswerRecords资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryAnswerRecordsResource {
    private final PoetryAnswerRecordsClient poetryAnswerRecordsClient;
    private final PoetryAnswerRecordsQueryMapper poetryAnswerRecordsQueryMapper;

    public PoetryAnswerRecordsResource(PoetryAnswerRecordsClient poetryAnswerRecordsClient,
                                       PoetryAnswerRecordsQueryMapper poetryAnswerRecordsQueryMapper) {
        this.poetryAnswerRecordsClient = poetryAnswerRecordsClient;
        this.poetryAnswerRecordsQueryMapper = poetryAnswerRecordsQueryMapper;
    }

    @GetMapping(value = "/poetryAnswerRecords", params = {"pageNo", "pageSize"})
    public Page<PoetryAnswerRecordsConsoleView> query(PoetryAnswerRecordsConsoleQuery query, Pageable pageable) {
        var PoetryAnswerRecordsQuery = poetryAnswerRecordsQueryMapper.convert(query);
        return poetryAnswerRecordsClient.query(PoetryAnswerRecordsQuery, pageable, PoetryAnswerRecordsConsoleView.class);
    }

}