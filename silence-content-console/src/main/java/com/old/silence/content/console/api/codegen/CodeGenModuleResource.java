package com.old.silence.content.console.api.codegen;

import java.math.BigInteger;

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
import com.old.silence.content.api.CodeGenModuleClient;
import com.old.silence.content.console.api.assembler.CodeGenModuleCommandMapper;
import com.old.silence.content.console.api.assembler.CodeGenModuleQueryMapper;
import com.old.silence.content.console.dto.CodeGenModuleConsoleCommand;
import com.old.silence.content.console.dto.CodeGenModuleConsoleQuery;
import com.old.silence.content.console.vo.CodeGenModuleConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;

/**
 * @author moryzang
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class CodeGenModuleResource {

    private final CodeGenModuleClient codeGenModuleClient;
    private final CodeGenModuleCommandMapper codeGenModuleCommandMapper;
    private final CodeGenModuleQueryMapper codeGenModuleQueryMapper;

    public CodeGenModuleResource(CodeGenModuleClient codeGenModuleClient,
                                 CodeGenModuleCommandMapper codeGenModuleCommandMapper,
                                 CodeGenModuleQueryMapper codeGenModuleQueryMapper) {
        this.codeGenModuleClient = codeGenModuleClient;
        this.codeGenModuleCommandMapper = codeGenModuleCommandMapper;
        this.codeGenModuleQueryMapper = codeGenModuleQueryMapper;
    }

    @GetMapping("/codeGenModules/{id}")
    public CodeGenModuleConsoleView findById(@PathVariable BigInteger id) {
        return codeGenModuleClient.findById(id, CodeGenModuleConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/codeGenModules", params = {"pageNo", "pageSize"})
    public Page<CodeGenModuleConsoleView> queryPage(CodeGenModuleConsoleQuery query, Pageable pageable) {
        var codeGenModuleQuery = codeGenModuleQueryMapper.convert(query);
        return codeGenModuleClient.queryPage(codeGenModuleQuery, pageable, CodeGenModuleConsoleView.class);
    }

    @PostMapping("/codeGenModules")
    public BigInteger create(@RequestBody CodeGenModuleConsoleCommand command) {
        var codeGenModule = codeGenModuleCommandMapper.convert(command);
        return codeGenModuleClient.create(codeGenModule);
    }
    
    @PutMapping("/codeGenModules/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody CodeGenModuleConsoleCommand command) {
        var codeGenModule = codeGenModuleCommandMapper.convert(command);
        codeGenModuleClient.update(id, codeGenModule);
    }

    @PutMapping("/codeGenModules/{id}/enable")
    public void enable(@PathVariable BigInteger id) {
        codeGenModuleClient.updateEnabledById(id, true);
    }

    @PutMapping("/codeGenModules/{id}/disable")
    public void disable(@PathVariable BigInteger id) {
        codeGenModuleClient.updateEnabledById(id, false);
    }

    @DeleteMapping("/codeGenModules/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        codeGenModuleClient.deleteById(id);
    }
}
