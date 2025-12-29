package com.old.silence.content.console.api.codegen;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.console.api.assembler.CodeApiDocumentCommandMapper;
import com.old.silence.content.console.api.assembler.CodeApiDocumentQueryMapper;
import com.old.silence.content.console.dto.CodeApiDocumentConsoleCommand;
import com.old.silence.content.console.dto.CodeApiDocumentConsoleQuery;
import com.old.silence.content.console.vo.CodeApiDocumentConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.content.api.CodeApiDocumentClient;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;
import java.util.List;

/**
* CodeApiDocument资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class CodeApiDocumentResource {
    private final CodeApiDocumentClient codeApiDocumentClient;
    private final CodeApiDocumentCommandMapper codeApiDocumentCommandMapper;
    private final CodeApiDocumentQueryMapper codeApiDocumentQueryMapper;

    public CodeApiDocumentResource(CodeApiDocumentClient codeApiDocumentClient,
                                CodeApiDocumentCommandMapper codeApiDocumentCommandMapper,
                                CodeApiDocumentQueryMapper codeApiDocumentQueryMapper) {
        this.codeApiDocumentClient = codeApiDocumentClient;
        this.codeApiDocumentCommandMapper = codeApiDocumentCommandMapper;
        this.codeApiDocumentQueryMapper = codeApiDocumentQueryMapper;
    }

    @GetMapping(value = "/codeApiDocuments/{id}")
    public CodeApiDocumentConsoleView findById(@PathVariable BigInteger id) {
        return codeApiDocumentClient.findById(id, CodeApiDocumentConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/codeApiDocuments", params = {"pageNo", "pageSize"})
    public Page<CodeApiDocumentConsoleView> query(CodeApiDocumentConsoleQuery query, Pageable pageable) {
        var codeApiDocumentQuery = codeApiDocumentQueryMapper.convert(query);
        return codeApiDocumentClient.query(codeApiDocumentQuery, pageable, CodeApiDocumentConsoleView.class);
    }

    @PostJsonMapping("/codeApiDocuments")
    public BigInteger create(@RequestBody CodeApiDocumentConsoleCommand command) {
        var codeApiDocumentCommand = codeApiDocumentCommandMapper.convert(command);
        return codeApiDocumentClient.create(codeApiDocumentCommand);
    }

    @PostJsonMapping("/codeApiDocuments/bulkCreate")
    public void bulkCreate(@RequestBody List<CodeApiDocumentConsoleCommand> commands) {
        var codeApiDocumentCommands = CollectionUtils.transformToList(commands, codeApiDocumentCommandMapper::convert);
        codeApiDocumentClient.bulkCreate(codeApiDocumentCommands);
    }

    @PutJsonMapping(value = "/codeApiDocuments/bulkUpdate")
    public void bulkUpdate(@RequestBody List<CodeApiDocumentConsoleCommand> commands) {
        var codeApiDocumentCommands = CollectionUtils.transformToList(commands, codeApiDocumentCommandMapper::convert);
        codeApiDocumentClient.bulkReplace(codeApiDocumentCommands);
    }

    @DeleteMapping("/codeApiDocuments/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        codeApiDocumentClient.deleteById(id);
    }
}