package com.old.silence.content.console.api.poetry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PromptTemplateClient;
import com.old.silence.content.console.api.assembler.PromptTemplateCommandMapper;
import com.old.silence.content.console.api.assembler.PromptTemplateQueryMapper;
import com.old.silence.content.console.dto.PromptTemplateConsoleCommand;
import com.old.silence.content.console.dto.PromptTemplateConsoleQuery;
import com.old.silence.content.console.vo.PromptTemplateConsoleView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;

/**
 * PromptTemplate资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PromptTemplateResource {
    private final PromptTemplateClient promptTemplateClient;
    private final PromptTemplateCommandMapper promptTemplateCommandMapper;
    private final PromptTemplateQueryMapper promptTemplateQueryMapper;

    public PromptTemplateResource(PromptTemplateClient promptTemplateClient,
                                  PromptTemplateCommandMapper promptTemplateCommandMapper,
                                  PromptTemplateQueryMapper promptTemplateQueryMapper) {
        this.promptTemplateClient = promptTemplateClient;
        this.promptTemplateCommandMapper = promptTemplateCommandMapper;
        this.promptTemplateQueryMapper = promptTemplateQueryMapper;
    }

    @GetMapping(value = "/promptTemplates", params = {"pageNo", "pageSize"})
    public Page<PromptTemplateConsoleView> query(PromptTemplateConsoleQuery query, Pageable pageable) {
        var PromptTemplateQuery = promptTemplateQueryMapper.convert(query);
        return promptTemplateClient.query(PromptTemplateQuery, pageable, PromptTemplateConsoleView.class);
    }

    @PostJsonMapping("/promptTemplates")
    public BigInteger create(PromptTemplateConsoleCommand command) {
        var promptTemplateCommand = promptTemplateCommandMapper.convert(command);
        return promptTemplateClient.create(promptTemplateCommand);
    }

    @PutJsonMapping(value = "/promptTemplates/{id}")
    public void update(@PathVariable BigInteger id, PromptTemplateConsoleCommand command) {
        var promptTemplateCommand = promptTemplateCommandMapper.convert(command);
        promptTemplateClient.update(id, promptTemplateCommand);
    }

    @DeleteMapping("/promptTemplates/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        promptTemplateClient.deleteById(id);
    }
}