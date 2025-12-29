package com.old.silence.content.console.api.codegen;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.CodeTableMetaClient;
import com.old.silence.content.console.api.assembler.CodeTableMetaCommandMapper;
import com.old.silence.content.console.api.assembler.CodeTableMetaQueryMapper;
import com.old.silence.content.console.dto.CodeApiDocumentConsoleCommand;
import com.old.silence.content.console.dto.CodeTableMetaConsoleCommand;
import com.old.silence.content.console.dto.CodeTableMetaConsoleQuery;
import com.old.silence.content.console.vo.CodeTableMetaConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;
import java.util.List;

/**
 * CodeTableMeta资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class CodeTableMetaResource {
    private final CodeTableMetaClient codeTableMetaClient;
    private final CodeTableMetaCommandMapper codeTableMetaCommandMapper;
    private final CodeTableMetaQueryMapper codeTableMetaQueryMapper;

    public CodeTableMetaResource(CodeTableMetaClient codeTableMetaClient,
                                 CodeTableMetaCommandMapper codeTableMetaCommandMapper,
                                 CodeTableMetaQueryMapper codeTableMetaQueryMapper) {
        this.codeTableMetaClient = codeTableMetaClient;
        this.codeTableMetaCommandMapper = codeTableMetaCommandMapper;
        this.codeTableMetaQueryMapper = codeTableMetaQueryMapper;
    }

    @GetMapping(value = "/codeTableMetas/{id}")
    public CodeTableMetaConsoleView findById(@PathVariable BigInteger id) {
        return codeTableMetaClient.findById(id, CodeTableMetaConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/codeTableMetas", params = {"pageNo", "pageSize"})
    public Page<CodeTableMetaConsoleView> query(CodeTableMetaConsoleQuery query, Pageable pageable) {
        var codeTableMetaQuery = codeTableMetaQueryMapper.convert(query);
        return codeTableMetaClient.query(codeTableMetaQuery, pageable, CodeTableMetaConsoleView.class);
    }

    @PostJsonMapping("/codeTableMetas")
    public BigInteger create(@RequestBody CodeTableMetaConsoleCommand command) {
        var codeTableMetaCommand = codeTableMetaCommandMapper.convert(command);
        return codeTableMetaClient.create(codeTableMetaCommand);
    }

    @PostJsonMapping("/codeTableMetas/bulkCreate")
    public void bulkCreate(@RequestBody List<CodeTableMetaConsoleCommand> commands) {
        var codeTableMetaCommands = CollectionUtils.transformToList(commands, codeTableMetaCommandMapper::convert);
        codeTableMetaClient.bulkCreate(codeTableMetaCommands);
    }

    @PutJsonMapping(value = "/codeTableMetas/bulkUpdate")
    public void bulkUpdate(@RequestBody List<CodeTableMetaConsoleCommand> commands) {
        var codeTableMetaCommands = CollectionUtils.transformToList(commands, codeTableMetaCommandMapper::convert);
        codeTableMetaClient.bulkReplace(codeTableMetaCommands);
    }

    @DeleteMapping("/codeTableMetas/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        codeTableMetaClient.deleteById(id);
    }
}