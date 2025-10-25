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
import com.old.silence.content.api.PoetryUserClient;
import com.old.silence.content.console.api.assembler.PoetryUserCommandMapper;
import com.old.silence.content.console.api.assembler.PoetryUserQueryMapper;
import com.old.silence.content.console.dto.PoetryUserConsoleCommand;
import com.old.silence.content.console.dto.PoetryUserConsoleQuery;
import com.old.silence.content.console.vo.PoetryUserConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;

import java.math.BigInteger;

/**
 * PoetryUser资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserResource {
    private final PoetryUserClient poetryUserClient;
    private final PoetryUserCommandMapper poetryUserCommandMapper;
    private final PoetryUserQueryMapper poetryUserQueryMapper;

    public PoetryUserResource(PoetryUserClient poetryUserClient,
                              PoetryUserCommandMapper poetryUserCommandMapper,
                              PoetryUserQueryMapper poetryUserQueryMapper) {
        this.poetryUserClient = poetryUserClient;
        this.poetryUserCommandMapper = poetryUserCommandMapper;
        this.poetryUserQueryMapper = poetryUserQueryMapper;
    }

    @GetMapping("/poetryUsers/{id}")
    public PoetryUserConsoleView findById(@PathVariable BigInteger id) {
        return poetryUserClient.findById(id, PoetryUserConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/poetryUsers", params = {"pageNo", "pageSize"})
    public Page<PoetryUserConsoleView> query(PoetryUserConsoleQuery query, Pageable pageable) {
        var poetryUserQuery = poetryUserQueryMapper.convert(query);
        return poetryUserClient.query(poetryUserQuery, pageable, PoetryUserConsoleView.class);
    }

    @PostMapping(value = "/poetryUsers")
    public String create(@RequestBody PoetryUserConsoleCommand command) {
        var poetryUserCommand = poetryUserCommandMapper.convert(command);
        return String.valueOf(poetryUserClient.create(poetryUserCommand));
    }

    @PutMapping("/poetryUsers/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody PoetryUserConsoleCommand command) {
        var poetryUserCommand = poetryUserCommandMapper.convert(command);
        poetryUserClient.update(id, poetryUserCommand);
    }

    @DeleteMapping("/poetryUsers/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        poetryUserClient.deleteById(id);
    }
}