package com.old.silence.content.console.api;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.CodeGenDatabaseClient;

import com.old.silence.content.console.api.assembler.CodeGenDatabaseCommandMapper;
import com.old.silence.content.console.api.assembler.CodeGenDatabaseQueryMapper;
import com.old.silence.content.console.dto.CodeGenDatabaseConsoleCommand;
import com.old.silence.content.console.dto.CodeGenDatabaseConsoleQuery;
import com.old.silence.content.console.vo.CodeGenDatabaseConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;

/**
 * @author moryzang
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class CodeGenDatabaseResource  {

    private final CodeGenDatabaseClient codeGenDatabaseClient;
    private final CodeGenDatabaseCommandMapper codeGenDatabaseCommandMapper;
    private final CodeGenDatabaseQueryMapper codeGenDatabaseQueryMapper;

    public CodeGenDatabaseResource(CodeGenDatabaseClient codeGenDatabaseClient,
                                   CodeGenDatabaseCommandMapper codeGenDatabaseCommandMapper,
                                   CodeGenDatabaseQueryMapper codeGenDatabaseQueryMapper) {
        this.codeGenDatabaseClient = codeGenDatabaseClient;
        this.codeGenDatabaseCommandMapper = codeGenDatabaseCommandMapper;
        this.codeGenDatabaseQueryMapper = codeGenDatabaseQueryMapper;
    }

    @GetMapping("/codeGenDatabases/{id}")
    public CodeGenDatabaseConsoleView findById(@PathVariable BigInteger id) {
        return codeGenDatabaseClient.findById(id, CodeGenDatabaseConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/codeGenDatabases", params = {"pageNo", "pageSize"})
    public Page<CodeGenDatabaseConsoleView> queryPage(CodeGenDatabaseConsoleQuery query, Pageable pageable) {
        var codeGenDatabaseQuery = codeGenDatabaseQueryMapper.convert(query);
        return codeGenDatabaseClient.queryPage(codeGenDatabaseQuery, pageable, CodeGenDatabaseConsoleView.class);
    }

    @PostMapping("/codeGenDatabases")
    public BigInteger create(@RequestBody CodeGenDatabaseConsoleCommand command) {
        var CodeGenDatabase = codeGenDatabaseCommandMapper.convert(command);
        return codeGenDatabaseClient.create(CodeGenDatabase);
    }
    @PutMapping("/codeGenDatabases/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody CodeGenDatabaseConsoleCommand command) {
        var CodeGenDatabase = codeGenDatabaseCommandMapper.convert(command);
        codeGenDatabaseClient.update(id, CodeGenDatabase);
    }
    @DeleteMapping("/codeGenDatabases/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        codeGenDatabaseClient.deleteById(id);
    }
}
