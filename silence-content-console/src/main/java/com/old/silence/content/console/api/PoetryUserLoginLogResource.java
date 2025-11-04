package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.console.api.assembler.PoetryUserLoginLogCommandMapper;
import com.old.silence.content.console.api.assembler.PoetryUserLoginLogQueryMapper;
import com.old.silence.content.console.dto.PoetryUserLoginLogConsoleCommand;
import com.old.silence.content.console.dto.PoetryUserLoginLogConsoleQuery;
import com.old.silence.content.console.vo.PoetryUserLoginLogConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.content.api.PoetryUserLoginLogClient;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;

/**
* PoetryUserLoginLog资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PoetryUserLoginLogResource {
    private final PoetryUserLoginLogClient poetryUserLoginLogClient;
    private final PoetryUserLoginLogCommandMapper poetryUserLoginLogCommandMapper;
    private final PoetryUserLoginLogQueryMapper poetryUserLoginLogQueryMapper;

    public PoetryUserLoginLogResource(PoetryUserLoginLogClient poetryUserLoginLogClient,
                                PoetryUserLoginLogCommandMapper poetryUserLoginLogCommandMapper,
                                PoetryUserLoginLogQueryMapper poetryUserLoginLogQueryMapper) {
        this.poetryUserLoginLogClient = poetryUserLoginLogClient;
        this.poetryUserLoginLogCommandMapper = poetryUserLoginLogCommandMapper;
        this.poetryUserLoginLogQueryMapper = poetryUserLoginLogQueryMapper;
    }

    @GetMapping(value = "/poetryUserLoginLogs/{id}")
    public PoetryUserLoginLogConsoleView findById(@PathVariable BigInteger id) {
        return poetryUserLoginLogClient.findById(id, PoetryUserLoginLogConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/poetryUserLoginLogs", params = {"pageNo", "pageSize"})
    public Page<PoetryUserLoginLogConsoleView> query(PoetryUserLoginLogConsoleQuery query, Pageable pageable) {
        var PoetryUserLoginLogQuery = poetryUserLoginLogQueryMapper.convert(query);
        return poetryUserLoginLogClient.query(PoetryUserLoginLogQuery, pageable, PoetryUserLoginLogConsoleView.class);
    }

    @PostJsonMapping("/poetryUserLoginLogs")
    public BigInteger create(PoetryUserLoginLogConsoleCommand command) {
        var poetryUserLoginLogCommand = poetryUserLoginLogCommandMapper.convert(command);
        return poetryUserLoginLogClient.create(poetryUserLoginLogCommand);
    }

    @PutJsonMapping(value = "/poetryUserLoginLogs/{id}")
    public void update(@PathVariable BigInteger id, PoetryUserLoginLogConsoleCommand command) {
        var poetryUserLoginLogCommand = poetryUserLoginLogCommandMapper.convert(command);
        poetryUserLoginLogClient.update(id, poetryUserLoginLogCommand);
    }

    @DeleteMapping("/poetryUserLoginLogs/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        poetryUserLoginLogClient.deleteById(id);
    }
}