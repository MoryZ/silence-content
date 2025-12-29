package com.old.silence.content.console.api.codegen;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.CodeFileSpecClient;
import com.old.silence.content.console.api.assembler.CodeFileSpecCommandMapper;
import com.old.silence.content.console.api.assembler.CodeFileSpecQueryMapper;
import com.old.silence.content.console.dto.CodeFileSpecConsoleCommand;
import com.old.silence.content.console.dto.CodeFileSpecConsoleQuery;
import com.old.silence.content.console.vo.CodeFileSpecConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;

/**
 * CodeFileSpec资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class CodeFileSpecResource {
    private final CodeFileSpecClient codeFileSpecClient;
    private final CodeFileSpecCommandMapper codeFileSpecCommandMapper;
    private final CodeFileSpecQueryMapper codeFileSpecQueryMapper;

    public CodeFileSpecResource(CodeFileSpecClient codeFileSpecClient,
                                CodeFileSpecCommandMapper codeFileSpecCommandMapper,
                                CodeFileSpecQueryMapper codeFileSpecQueryMapper) {
        this.codeFileSpecClient = codeFileSpecClient;
        this.codeFileSpecCommandMapper = codeFileSpecCommandMapper;
        this.codeFileSpecQueryMapper = codeFileSpecQueryMapper;
    }

    @GetMapping(value = "/codeFileSpecs/{id}")
    public CodeFileSpecConsoleView findById(@PathVariable BigInteger id) {
        return codeFileSpecClient.findById(id, CodeFileSpecConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/codeFileSpecs", params = {"pageNo", "pageSize"})
    public Page<CodeFileSpecConsoleView> query(CodeFileSpecConsoleQuery query, Pageable pageable) {
        var codeFileSpecQuery = codeFileSpecQueryMapper.convert(query);
        return codeFileSpecClient.query(codeFileSpecQuery, pageable, CodeFileSpecConsoleView.class);
    }

    @PostJsonMapping("/codeFileSpecs")
    public BigInteger create(@RequestBody CodeFileSpecConsoleCommand command) {
        var codeFileSpecCommand = codeFileSpecCommandMapper.convert(command);
        return codeFileSpecClient.create(codeFileSpecCommand);
    }

    @PutJsonMapping(value = "/codeFileSpecs/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody CodeFileSpecConsoleCommand command) {
        var codeFileSpecCommand = codeFileSpecCommandMapper.convert(command);
        codeFileSpecClient.update(id, codeFileSpecCommand);
    }

    @DeleteMapping("/codeFileSpecs/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        codeFileSpecClient.deleteById(id);
    }
}