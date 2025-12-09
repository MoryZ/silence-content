package com.old.silence.content.console.api.codegen;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import com.old.silence.content.api.FreemarkerTemplatesClient;
import com.old.silence.content.console.api.assembler.FreemarkerTemplatesCommandMapper;
import com.old.silence.content.console.api.assembler.FreemarkerTemplatesQueryMapper;
import com.old.silence.content.console.dto.FreemarkerTemplatesConsoleCommand;
import com.old.silence.content.console.dto.FreemarkerTemplatesConsoleQuery;
import com.old.silence.content.console.vo.FreemarkerTemplatesConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;

/**
 * FreemarkerTemplates资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class FreemarkerTemplatesResource {
    private final FreemarkerTemplatesClient freemarkerTemplatesClient;
    private final FreemarkerTemplatesCommandMapper freemarkerTemplatesCommandMapper;
    private final FreemarkerTemplatesQueryMapper freemarkerTemplatesQueryMapper;

    public FreemarkerTemplatesResource(FreemarkerTemplatesClient freemarkerTemplatesClient,
                                       FreemarkerTemplatesCommandMapper freemarkerTemplatesCommandMapper,
                                       FreemarkerTemplatesQueryMapper freemarkerTemplatesQueryMapper) {
        this.freemarkerTemplatesClient = freemarkerTemplatesClient;
        this.freemarkerTemplatesCommandMapper = freemarkerTemplatesCommandMapper;
        this.freemarkerTemplatesQueryMapper = freemarkerTemplatesQueryMapper;
    }

    @GetMapping(value = "/freemarkerTemplates/{id}")
    public FreemarkerTemplatesConsoleView findById(@PathVariable BigInteger id) {
        return freemarkerTemplatesClient.findById(id, FreemarkerTemplatesConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/freemarkerTemplates", params = {"pageNo", "pageSize"})
    public Page<FreemarkerTemplatesConsoleView> query(FreemarkerTemplatesConsoleQuery query, Pageable pageable) {
        var FreemarkerTemplatesQuery = freemarkerTemplatesQueryMapper.convert(query);
        return freemarkerTemplatesClient.query(FreemarkerTemplatesQuery, pageable, FreemarkerTemplatesConsoleView.class);
    }

    @PostJsonMapping("/freemarkerTemplates")
    public BigInteger create(@RequestBody FreemarkerTemplatesConsoleCommand command) {
        var freemarkerTemplatesCommand = freemarkerTemplatesCommandMapper.convert(command);
        return freemarkerTemplatesClient.create(freemarkerTemplatesCommand);
    }

    @PutJsonMapping(value = "/freemarkerTemplates/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody FreemarkerTemplatesConsoleCommand command) {
        var freemarkerTemplatesCommand = freemarkerTemplatesCommandMapper.convert(command);
        freemarkerTemplatesClient.update(id, freemarkerTemplatesCommand);
    }

    @DeleteMapping("/freemarkerTemplates/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        freemarkerTemplatesClient.deleteById(id);
    }
}