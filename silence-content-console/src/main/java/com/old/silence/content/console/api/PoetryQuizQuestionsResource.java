package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.console.api.assembler.PoetryQuizQuestionsCommandMapper;
import com.old.silence.content.console.api.assembler.PoetryQuizQuestionsQueryMapper;
import com.old.silence.content.console.dto.PoetryQuizQuestionsConsoleCommand;
import com.old.silence.content.console.dto.PoetryQuizQuestionsConsoleQuery;
import com.old.silence.content.console.vo.PoetryQuizQuestionsConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.content.api.PoetryQuizQuestionsClient;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;

/**
* PoetryQuizQuestions资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PoetryQuizQuestionsResource {
    private final PoetryQuizQuestionsClient poetryQuizQuestionsClient;
    private final PoetryQuizQuestionsCommandMapper poetryQuizQuestionsCommandMapper;
    private final PoetryQuizQuestionsQueryMapper poetryQuizQuestionsQueryMapper;

    public PoetryQuizQuestionsResource(PoetryQuizQuestionsClient poetryQuizQuestionsClient,
                                PoetryQuizQuestionsCommandMapper poetryQuizQuestionsCommandMapper,
                                PoetryQuizQuestionsQueryMapper poetryQuizQuestionsQueryMapper) {
        this.poetryQuizQuestionsClient = poetryQuizQuestionsClient;
        this.poetryQuizQuestionsCommandMapper = poetryQuizQuestionsCommandMapper;
        this.poetryQuizQuestionsQueryMapper = poetryQuizQuestionsQueryMapper;
    }

    @GetMapping(value = "/poetryQuizQuestions/{id}")
    public PoetryQuizQuestionsConsoleView findById(@PathVariable BigInteger id) {
        return poetryQuizQuestionsClient.findById(id, PoetryQuizQuestionsConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/poetryQuizQuestions", params = {"pageNo", "pageSize"})
    public Page<PoetryQuizQuestionsConsoleView> query(PoetryQuizQuestionsConsoleQuery query, Pageable pageable) {
        var PoetryQuizQuestionsQuery = poetryQuizQuestionsQueryMapper.convert(query);
        return poetryQuizQuestionsClient.query(PoetryQuizQuestionsQuery, pageable, PoetryQuizQuestionsConsoleView.class);
    }

    @PostJsonMapping("/poetryQuizQuestions")
    public BigInteger create(PoetryQuizQuestionsConsoleCommand command) {
        var poetryQuizQuestionsCommand = poetryQuizQuestionsCommandMapper.convert(command);
        return poetryQuizQuestionsClient.create(poetryQuizQuestionsCommand);
    }

    @PutJsonMapping(value = "/poetryQuizQuestions/{id}")
    public void update(@PathVariable BigInteger id, PoetryQuizQuestionsConsoleCommand command) {
        var poetryQuizQuestionsCommand = poetryQuizQuestionsCommandMapper.convert(command);
        poetryQuizQuestionsClient.update(id, poetryQuizQuestionsCommand);
    }

    @DeleteMapping("/poetryQuizQuestions/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        poetryQuizQuestionsClient.deleteById(id);
    }
}