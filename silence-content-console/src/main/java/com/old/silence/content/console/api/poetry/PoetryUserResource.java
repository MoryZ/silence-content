package com.old.silence.content.console.api.poetry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryUserClient;
import com.old.silence.content.console.api.assembler.PoetryUserQueryMapper;
import com.old.silence.content.console.dto.PoetryUserConsoleQuery;
import com.old.silence.content.console.vo.PoetryUserConsoleView;

/**
 * PoetryUser资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserResource {
    private final PoetryUserClient poetryUserClient;
    private final PoetryUserQueryMapper poetryUserQueryMapper;

    public PoetryUserResource(PoetryUserClient poetryUserClient,
                              PoetryUserQueryMapper poetryUserQueryMapper) {
        this.poetryUserClient = poetryUserClient;
        this.poetryUserQueryMapper = poetryUserQueryMapper;
    }


    @GetMapping(value = "/poetryUsers", params = {"pageNo", "pageSize"})
    public Page<PoetryUserConsoleView> query(PoetryUserConsoleQuery query, Pageable pageable) {
        var poetryUserQuery = poetryUserQueryMapper.convert(query);
        return poetryUserClient.query(poetryUserQuery, pageable, PoetryUserConsoleView.class);
    }

}