package com.old.silence.content.console.api;

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
import com.old.silence.content.api.PoetryUserStudySettingClient;
import com.old.silence.content.console.api.assembler.PoetryUserStudySettingCommandMapper;
import com.old.silence.content.console.api.assembler.PoetryUserStudySettingQueryMapper;
import com.old.silence.content.console.dto.PoetryUserStudySettingConsoleCommand;
import com.old.silence.content.console.dto.PoetryUserStudySettingConsoleQuery;
import com.old.silence.content.console.vo.PoetryUserStudySettingConsoleView;

import java.math.BigInteger;


/**
 * PoetryUserStudySetting资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserStudySettingResource {
    private final PoetryUserStudySettingClient poetryUserStudySettingClient;
    private final PoetryUserStudySettingCommandMapper poetryUserStudySettingCommandMapper;
    private final PoetryUserStudySettingQueryMapper poetryUserStudySettingQueryMapper;

    public PoetryUserStudySettingResource(PoetryUserStudySettingClient poetryUserStudySettingClient,
                                          PoetryUserStudySettingCommandMapper poetryUserStudySettingCommandMapper,
                                          PoetryUserStudySettingQueryMapper poetryUserStudySettingQueryMapper) {
        this.poetryUserStudySettingClient = poetryUserStudySettingClient;
        this.poetryUserStudySettingCommandMapper = poetryUserStudySettingCommandMapper;
        this.poetryUserStudySettingQueryMapper = poetryUserStudySettingQueryMapper;
    }


    @GetMapping("/poetryUserStudySettings/{subCategoryId}/{gradeId}/{userId}")
    public PoetryUserStudySettingConsoleView findBySubCategoryIdGradeIdAndUserId(@PathVariable BigInteger subCategoryId, @PathVariable BigInteger gradeId, @PathVariable BigInteger userId) {
        return poetryUserStudySettingClient.findBySubCategoryIdGradeIdAndUserId(subCategoryId, gradeId, userId, PoetryUserStudySettingConsoleView.class)
                .orElse(null);
    }

    @GetMapping(value = "/poetryUserStudySettings", params = {"pageNo", "pageSize"})
    public Page<PoetryUserStudySettingConsoleView> query(PoetryUserStudySettingConsoleQuery query, Pageable pageable) {
        var poetryUserStudySettingQuery = poetryUserStudySettingQueryMapper.convert(query);
        return poetryUserStudySettingClient.query(poetryUserStudySettingQuery, pageable, PoetryUserStudySettingConsoleView.class);
    }

    @PostMapping(value = "/poetryUserStudySettings")
    public String create(@RequestBody PoetryUserStudySettingConsoleCommand command) {
        var poetryUserStudySettingCommand = poetryUserStudySettingCommandMapper.convert(command);
        return String.valueOf(poetryUserStudySettingClient.create(poetryUserStudySettingCommand));
    }

    @PutMapping("/poetryUserStudySettings/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody PoetryUserStudySettingConsoleCommand command) {
        var poetryUserStudySettingCommand = poetryUserStudySettingCommandMapper.convert(command);
        poetryUserStudySettingClient.update(id, poetryUserStudySettingCommand);
    }

    @DeleteMapping("/poetryUserStudySettings/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        poetryUserStudySettingClient.deleteById(id);
    }
}