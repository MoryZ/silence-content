package com.old.silence.content.console.api.codegen;

import java.math.BigInteger;
import java.sql.DriverManager;

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

    @GetMapping("/codeGenDatabases/{id}/test-connection")
    public boolean testConnection(@PathVariable BigInteger id) {
        var codeGenDatabase = findById(id);
        try (var connection = DriverManager.getConnection(
                codeGenDatabase.getDatabaseUrl(),
                codeGenDatabase.getUsername(),
                codeGenDatabase.getPassword())) {
            return connection.isValid(2); // 检查连接是否有效，超时时间为2秒
        } catch (Exception e) {
            return false; // 如果连接失败，返回false
        }
    }

    @GetMapping(value = "/codeGenDatabases", params = {"pageNo", "pageSize"})
    public Page<CodeGenDatabaseConsoleView> queryPage(CodeGenDatabaseConsoleQuery query, Pageable pageable) {
        var codeGenDatabaseQuery = codeGenDatabaseQueryMapper.convert(query);
        return codeGenDatabaseClient.queryPage(codeGenDatabaseQuery, pageable, CodeGenDatabaseConsoleView.class);
    }

    @PostMapping("/codeGenDatabases")
    public BigInteger create(@RequestBody CodeGenDatabaseConsoleCommand command) {
        var codeGenDatabase = codeGenDatabaseCommandMapper.convert(command);
        return codeGenDatabaseClient.create(codeGenDatabase);
    }
    @PutMapping("/codeGenDatabases/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody CodeGenDatabaseConsoleCommand command) {
        var codeGenDatabase = codeGenDatabaseCommandMapper.convert(command);
        codeGenDatabaseClient.update(id, codeGenDatabase);
    }

    @DeleteMapping("/codeGenDatabases/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        codeGenDatabaseClient.deleteById(id);
    }
}
