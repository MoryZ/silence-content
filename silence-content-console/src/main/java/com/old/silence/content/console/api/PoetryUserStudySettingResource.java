package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryUserStudySettingClient;
import com.old.silence.content.console.api.assembler.PoetryUserStudySettingQueryMapper;
import com.old.silence.content.console.dto.PoetryUserStudySettingConsoleQuery;
import com.old.silence.content.console.vo.PoetryUserStudySettingConsoleView;


/**
 * PoetryUserStudySetting资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserStudySettingResource {
    private final PoetryUserStudySettingClient poetryUserStudySettingClient;
    private final PoetryUserStudySettingQueryMapper poetryUserStudySettingQueryMapper;

    public PoetryUserStudySettingResource(PoetryUserStudySettingClient poetryUserStudySettingClient,
                                          PoetryUserStudySettingQueryMapper poetryUserStudySettingQueryMapper) {
        this.poetryUserStudySettingClient = poetryUserStudySettingClient;
        this.poetryUserStudySettingQueryMapper = poetryUserStudySettingQueryMapper;
    }

    @GetMapping(value = "/poetryUserStudySettings", params = {"pageNo", "pageSize"})
    public Page<PoetryUserStudySettingConsoleView> query(PoetryUserStudySettingConsoleQuery query, Pageable pageable) {
        var poetryUserStudySettingQuery = poetryUserStudySettingQueryMapper.convert(query);
        return poetryUserStudySettingClient.query(poetryUserStudySettingQuery, pageable, PoetryUserStudySettingConsoleView.class);
    }

}