package com.old.silence.content.console.api.poetry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.console.api.assembler.PoetryUserLoginLogQueryMapper;
import com.old.silence.content.console.dto.PoetryUserLoginLogConsoleQuery;
import com.old.silence.content.console.vo.PoetryUserLoginLogConsoleView;
import com.old.silence.content.api.PoetryUserLoginLogClient;

/**
* PoetryUserLoginLog资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PoetryUserLoginLogResource {
    private final PoetryUserLoginLogClient poetryUserLoginLogClient;
    private final PoetryUserLoginLogQueryMapper poetryUserLoginLogQueryMapper;

    public PoetryUserLoginLogResource(PoetryUserLoginLogClient poetryUserLoginLogClient,
                                PoetryUserLoginLogQueryMapper poetryUserLoginLogQueryMapper) {
        this.poetryUserLoginLogClient = poetryUserLoginLogClient;
        this.poetryUserLoginLogQueryMapper = poetryUserLoginLogQueryMapper;
    }

    @GetMapping(value = "/poetryUserLoginLogs", params = {"pageNo", "pageSize"})
    public Page<PoetryUserLoginLogConsoleView> query(PoetryUserLoginLogConsoleQuery query, Pageable pageable) {
        var PoetryUserLoginLogQuery = poetryUserLoginLogQueryMapper.convert(query);
        return poetryUserLoginLogClient.query(PoetryUserLoginLogQuery, pageable, PoetryUserLoginLogConsoleView.class);
    }


}