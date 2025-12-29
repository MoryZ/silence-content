package com.old.silence.content.console.api.codegen;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.CodeFileTemplateClient;
import com.old.silence.content.console.api.assembler.CodeFileTemplateCommandMapper;
import com.old.silence.content.console.api.assembler.CodeFileTemplateQueryMapper;
import com.old.silence.content.console.dto.CodeFileTemplateConsoleCommand;
import com.old.silence.content.console.dto.CodeFileTemplateConsoleQuery;
import com.old.silence.content.console.vo.CodeFileTemplateConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;

/**
 * FreemarkerTemplates资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class CodeFileTemplateResource {
    private final CodeFileTemplateClient freemarkerTemplatesClient;
    private final CodeFileTemplateCommandMapper codeFileTemplateCommandMapper;
    private final CodeFileTemplateQueryMapper codeFileTemplateQueryMapper;

    public CodeFileTemplateResource(CodeFileTemplateClient freemarkerTemplatesClient,
                                    CodeFileTemplateCommandMapper codeFileTemplateCommandMapper,
                                    CodeFileTemplateQueryMapper codeFileTemplateQueryMapper) {
        this.freemarkerTemplatesClient = freemarkerTemplatesClient;
        this.codeFileTemplateCommandMapper = codeFileTemplateCommandMapper;
        this.codeFileTemplateQueryMapper = codeFileTemplateQueryMapper;
    }

    @GetMapping(value = "/codeFileTemplates/{id}")
    public CodeFileTemplateConsoleView findById(@PathVariable BigInteger id) {
        return freemarkerTemplatesClient.findById(id, CodeFileTemplateConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/codeFileTemplates", params = {"pageNo", "pageSize"})
    public Page<CodeFileTemplateConsoleView> query(CodeFileTemplateConsoleQuery query, Pageable pageable) {
        var freemarkerTemplatesQuery = codeFileTemplateQueryMapper.convert(query);
        return freemarkerTemplatesClient.query(freemarkerTemplatesQuery, pageable, CodeFileTemplateConsoleView.class);
    }

    @PostJsonMapping("/codeFileTemplates")
    public BigInteger create(@RequestBody CodeFileTemplateConsoleCommand command) {
        var codeFileTemplateCommand = codeFileTemplateCommandMapper.convert(command);
        return freemarkerTemplatesClient.create(codeFileTemplateCommand);
    }

    @PutJsonMapping(value = "/codeFileTemplates/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody CodeFileTemplateConsoleCommand command) {
        var codeFileTemplateCommand = codeFileTemplateCommandMapper.convert(command);
        freemarkerTemplatesClient.update(id, codeFileTemplateCommand);
    }

    @DeleteMapping("/codeFileTemplates/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        freemarkerTemplatesClient.deleteById(id);
    }
}