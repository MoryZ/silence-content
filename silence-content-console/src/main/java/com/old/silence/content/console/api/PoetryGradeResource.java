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
import com.old.silence.content.api.PoetryGradeClient;
import com.old.silence.content.console.api.assembler.PoetryGradeCommandMapper;
import com.old.silence.content.console.api.assembler.PoetryGradeQueryMapper;
import com.old.silence.content.console.dto.PoetryGradeConsoleCommand;
import com.old.silence.content.console.dto.PoetryGradeConsoleQuery;
import com.old.silence.content.console.vo.PoetryGradeConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;

import java.math.BigInteger;

/**
 * PoetryGrade资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryGradeResource {
    private final PoetryGradeClient poetryGradeClient;
    private final PoetryGradeCommandMapper poetryGradeCommandMapper;
    private final PoetryGradeQueryMapper poetryGradeQueryMapper;

    public PoetryGradeResource(PoetryGradeClient poetryGradeClient,
                               PoetryGradeCommandMapper poetryGradeCommandMapper,
                               PoetryGradeQueryMapper poetryGradeQueryMapper) {
        this.poetryGradeClient = poetryGradeClient;
        this.poetryGradeCommandMapper = poetryGradeCommandMapper;
        this.poetryGradeQueryMapper = poetryGradeQueryMapper;
    }

    @GetMapping("/poetryGrades/{id}")
    public PoetryGradeConsoleView findById(@PathVariable BigInteger id) {
        return poetryGradeClient.findById(id, PoetryGradeConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/poetryGrades", params = {"pageNo", "pageSize"})
    public Page<PoetryGradeConsoleView> query(PoetryGradeConsoleQuery query, Pageable pageable) {
        var poetryGradeQuery = poetryGradeQueryMapper.convert(query);
        return poetryGradeClient.query(poetryGradeQuery, pageable, PoetryGradeConsoleView.class);
    }

    @PostMapping(value = "/poetryGrades")
    public String create(@RequestBody PoetryGradeConsoleCommand command) {
        var poetryGradeCommand = poetryGradeCommandMapper.convert(command);
        return String.valueOf(poetryGradeClient.create(poetryGradeCommand));
    }

    @PutMapping("/poetryGrades/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody PoetryGradeConsoleCommand command) {
        var poetryGradeCommand = poetryGradeCommandMapper.convert(command);
        poetryGradeClient.update(id, poetryGradeCommand);
    }

    @DeleteMapping("/poetryGrades/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        poetryGradeClient.deleteById(id);
    }
}