package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.console.api.assembler.PoetryAnswerRecordsCommandMapper;
import com.old.silence.content.console.api.assembler.PoetryAnswerRecordsQueryMapper;
import com.old.silence.content.console.dto.PoetryAnswerRecordsConsoleCommand;
import com.old.silence.content.console.dto.PoetryAnswerRecordsConsoleQuery;
import com.old.silence.content.console.vo.PoetryAnswerRecordsConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.content.api.PoetryAnswerRecordsClient;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;

/**
* PoetryAnswerRecords资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PoetryAnswerRecordsResource {
    private final PoetryAnswerRecordsClient poetryAnswerRecordsClient;
    private final PoetryAnswerRecordsCommandMapper poetryAnswerRecordsCommandMapper;
    private final PoetryAnswerRecordsQueryMapper poetryAnswerRecordsQueryMapper;

    public PoetryAnswerRecordsResource(PoetryAnswerRecordsClient poetryAnswerRecordsClient,
                                PoetryAnswerRecordsCommandMapper poetryAnswerRecordsCommandMapper,
                                PoetryAnswerRecordsQueryMapper poetryAnswerRecordsQueryMapper) {
        this.poetryAnswerRecordsClient = poetryAnswerRecordsClient;
        this.poetryAnswerRecordsCommandMapper = poetryAnswerRecordsCommandMapper;
        this.poetryAnswerRecordsQueryMapper = poetryAnswerRecordsQueryMapper;
    }

    @GetMapping(value = "/poetryAnswerRecords", params = {"pageNo", "pageSize"})
    public Page<PoetryAnswerRecordsConsoleView> query(PoetryAnswerRecordsConsoleQuery query, Pageable pageable) {
        var PoetryAnswerRecordsQuery = poetryAnswerRecordsQueryMapper.convert(query);
        return poetryAnswerRecordsClient.query(PoetryAnswerRecordsQuery, pageable, PoetryAnswerRecordsConsoleView.class);
    }

    @PostJsonMapping("/poetryAnswerRecords")
    public BigInteger create(PoetryAnswerRecordsConsoleCommand command) {
        var poetryAnswerRecordsCommand = poetryAnswerRecordsCommandMapper.convert(command);
        return poetryAnswerRecordsClient.create(poetryAnswerRecordsCommand);
    }

    @PutJsonMapping(value = "/poetryAnswerRecords/{id}")
    public void update(@PathVariable BigInteger id, PoetryAnswerRecordsConsoleCommand command) {
        var poetryAnswerRecordsCommand = poetryAnswerRecordsCommandMapper.convert(command);
        poetryAnswerRecordsClient.update(id, poetryAnswerRecordsCommand);
    }

    @DeleteMapping("/poetryAnswerRecords/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        poetryAnswerRecordsClient.deleteById(id);
    }
}