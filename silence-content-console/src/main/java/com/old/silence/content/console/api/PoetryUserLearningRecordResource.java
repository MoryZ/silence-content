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
import com.old.silence.content.api.PoetryUserLearningRecordClient;
import com.old.silence.content.console.api.assembler.PoetryUserLearningRecordCommandMapper;
import com.old.silence.content.console.api.assembler.PoetryUserLearningRecordQueryMapper;
import com.old.silence.content.console.dto.PoetryUserLearningRecordConsoleCommand;
import com.old.silence.content.console.dto.PoetryUserLearningRecordConsoleQuery;
import com.old.silence.content.console.vo.PoetryUserLearningRecordConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;

/**
 * PoetryUserLearningRecord资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserLearningRecordResource {
    private final PoetryUserLearningRecordClient poetryUserLearningRecordClient;
    private final PoetryUserLearningRecordCommandMapper poetryUserLearningRecordCommandMapper;
    private final PoetryUserLearningRecordQueryMapper poetryUserLearningRecordQueryMapper;

    public PoetryUserLearningRecordResource(PoetryUserLearningRecordClient poetryUserLearningRecordClient,
                                            PoetryUserLearningRecordCommandMapper poetryUserLearningRecordCommandMapper,
                                            PoetryUserLearningRecordQueryMapper poetryUserLearningRecordQueryMapper) {
        this.poetryUserLearningRecordClient = poetryUserLearningRecordClient;
        this.poetryUserLearningRecordCommandMapper = poetryUserLearningRecordCommandMapper;
        this.poetryUserLearningRecordQueryMapper = poetryUserLearningRecordQueryMapper;
    }

    @GetMapping("/poetryUserLearningRecords/{id}")
    public PoetryUserLearningRecordConsoleView findById(@PathVariable BigInteger id) {
        return poetryUserLearningRecordClient.findById(id, PoetryUserLearningRecordConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/poetryUserLearningRecords", params = {"pageNo", "pageSize"})
    public Page<PoetryUserLearningRecordConsoleView> query(PoetryUserLearningRecordConsoleQuery query, Pageable pageable) {
        var poetryUserLearningRecordQuery = poetryUserLearningRecordQueryMapper.convert(query);
        return poetryUserLearningRecordClient.query(poetryUserLearningRecordQuery, pageable, PoetryUserLearningRecordConsoleView.class);
    }

    @PostMapping(value = "/poetryUserLearningRecords")
    public String create(@RequestBody PoetryUserLearningRecordConsoleCommand command) {
        var poetryUserLearningRecordCommand = poetryUserLearningRecordCommandMapper.convert(command);
        return String.valueOf(poetryUserLearningRecordClient.create(poetryUserLearningRecordCommand));
    }

    @PutMapping("/poetryUserLearningRecords/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody PoetryUserLearningRecordConsoleCommand command) {
        var poetryUserLearningRecordCommand = poetryUserLearningRecordCommandMapper.convert(command);
        poetryUserLearningRecordClient.update(id, poetryUserLearningRecordCommand);
    }

    @DeleteMapping("/poetryUserLearningRecords/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        poetryUserLearningRecordClient.deleteById(id);
    }
}