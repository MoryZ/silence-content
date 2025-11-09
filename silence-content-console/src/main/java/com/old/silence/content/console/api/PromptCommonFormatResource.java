package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.console.api.assembler.PromptCommonFormatCommandMapper;
import com.old.silence.content.console.api.assembler.PromptCommonFormatQueryMapper;
import com.old.silence.content.console.dto.PromptCommonFormatConsoleCommand;
import com.old.silence.content.console.dto.PromptCommonFormatConsoleQuery;
import com.old.silence.content.console.vo.PromptCommonFormatConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.content.api.PromptCommonFormatClient;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;

/**
* PromptCommonFormat资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PromptCommonFormatResource {
    private final PromptCommonFormatClient promptCommonFormatClient;
    private final PromptCommonFormatCommandMapper promptCommonFormatCommandMapper;
    private final PromptCommonFormatQueryMapper promptCommonFormatQueryMapper;

    public PromptCommonFormatResource(PromptCommonFormatClient promptCommonFormatClient,
                                PromptCommonFormatCommandMapper promptCommonFormatCommandMapper,
                                PromptCommonFormatQueryMapper promptCommonFormatQueryMapper) {
        this.promptCommonFormatClient = promptCommonFormatClient;
        this.promptCommonFormatCommandMapper = promptCommonFormatCommandMapper;
        this.promptCommonFormatQueryMapper = promptCommonFormatQueryMapper;
    }

    @GetMapping(value = "/promptCommonFormats/{id}")
    public PromptCommonFormatConsoleView findById(@PathVariable BigInteger id) {
        return promptCommonFormatClient.findById(id, PromptCommonFormatConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/promptCommonFormats", params = {"pageNo", "pageSize"})
    public Page<PromptCommonFormatConsoleView> query(PromptCommonFormatConsoleQuery query, Pageable pageable) {
        var PromptCommonFormatQuery = promptCommonFormatQueryMapper.convert(query);
        return promptCommonFormatClient.query(PromptCommonFormatQuery, pageable, PromptCommonFormatConsoleView.class);
    }

    @PostJsonMapping("/promptCommonFormats")
    public BigInteger create(PromptCommonFormatConsoleCommand command) {
        var promptCommonFormatCommand = promptCommonFormatCommandMapper.convert(command);
        return promptCommonFormatClient.create(promptCommonFormatCommand);
    }

    @PutJsonMapping(value = "/promptCommonFormats/{id}")
    public void update(@PathVariable BigInteger id, PromptCommonFormatConsoleCommand command) {
        var promptCommonFormatCommand = promptCommonFormatCommandMapper.convert(command);
        promptCommonFormatClient.update(id, promptCommonFormatCommand);
    }

    @DeleteMapping("/promptCommonFormats/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        promptCommonFormatClient.deleteById(id);
    }
}