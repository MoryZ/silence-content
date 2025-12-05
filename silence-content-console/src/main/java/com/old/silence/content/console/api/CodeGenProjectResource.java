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
import com.old.silence.content.api.CodeGenProjectClient;
import com.old.silence.content.console.api.assembler.CodeGenProjectCommandMapper;
import com.old.silence.content.console.api.assembler.CodeGenProjectQueryMapper;
import com.old.silence.content.console.dto.CodeGenProjectConsoleCommand;
import com.old.silence.content.console.dto.CodeGenProjectConsoleQuery;
import com.old.silence.content.console.vo.CodeGenProjectConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;

/**
 * @author moryzang
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class CodeGenProjectResource {

    private final CodeGenProjectClient codeGenProjectClient;
    private final CodeGenProjectCommandMapper codeGenProjectCommandMapper;
    private final CodeGenProjectQueryMapper codeGenProjectQueryMapper;

    public CodeGenProjectResource(CodeGenProjectClient codeGenProjectClient,
                                  CodeGenProjectCommandMapper codeGenProjectCommandMapper,
                                  CodeGenProjectQueryMapper codeGenProjectQueryMapper) {
        this.codeGenProjectClient = codeGenProjectClient;
        this.codeGenProjectCommandMapper = codeGenProjectCommandMapper;
        this.codeGenProjectQueryMapper = codeGenProjectQueryMapper;
    }

    @GetMapping("/codeGenProjects/{id}")
    public CodeGenProjectConsoleView findById(@PathVariable BigInteger id) {
        return codeGenProjectClient.findById(id, CodeGenProjectConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/codeGenProjects", params = {"pageNo", "pageSize"})
    public Page<CodeGenProjectConsoleView> queryPage(CodeGenProjectConsoleQuery query, Pageable pageable) {
        var codeGenProjectQuery = codeGenProjectQueryMapper.convert(query);
        return codeGenProjectClient.queryPage(codeGenProjectQuery, pageable, CodeGenProjectConsoleView.class);
    }

    @PostMapping("/codeGenProjects")
    public BigInteger create(@RequestBody CodeGenProjectConsoleCommand command) {
        var CodeGenProject = codeGenProjectCommandMapper.convert(command);
        return codeGenProjectClient.create(CodeGenProject);
    }
    @PutMapping("/codeGenProjects/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody CodeGenProjectConsoleCommand command) {
        var CodeGenProject = codeGenProjectCommandMapper.convert(command);
        codeGenProjectClient.update(id, CodeGenProject);
    }
    @DeleteMapping("/codeGenProjects/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        codeGenProjectClient.deleteById(id);
    }
}
