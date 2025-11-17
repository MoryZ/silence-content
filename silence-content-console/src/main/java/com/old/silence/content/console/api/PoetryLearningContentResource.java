package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryLearningContentClient;
import com.old.silence.content.console.api.assembler.PoetryLearningContentCommandMapper;
import com.old.silence.content.console.api.assembler.PoetryLearningContentQueryMapper;
import com.old.silence.content.console.dto.PoetryLeaningContentGenerateCommand;
import com.old.silence.content.console.dto.PoetryLearningContentConsoleCommand;
import com.old.silence.content.console.dto.PoetryLearningContentConsoleQuery;
import com.old.silence.content.console.service.PoetryLearningContentGenerationConsoleService;
import com.old.silence.content.console.vo.PoetryLearningContentConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.core.util.CollectionUtils;

import java.math.BigInteger;
import java.util.List;


/**
 * PoetryLearningContent资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryLearningContentResource {
    private final PoetryLearningContentGenerationConsoleService poetryLearningContentGenerationConsoleService;
    private final PoetryLearningContentClient poetryLearningContentClient;
    private final PoetryLearningContentCommandMapper poetryLearningContentCommandMapper;
    private final PoetryLearningContentQueryMapper poetryLearningContentQueryMapper;

    public PoetryLearningContentResource(PoetryLearningContentGenerationConsoleService poetryLearningContentGenerationConsoleService,
                                         PoetryLearningContentClient poetryLearningContentClient,
                                         PoetryLearningContentCommandMapper poetryLearningContentCommandMapper,
                                         PoetryLearningContentQueryMapper poetryLearningContentQueryMapper) {
        this.poetryLearningContentGenerationConsoleService = poetryLearningContentGenerationConsoleService;
        this.poetryLearningContentClient = poetryLearningContentClient;
        this.poetryLearningContentCommandMapper = poetryLearningContentCommandMapper;
        this.poetryLearningContentQueryMapper = poetryLearningContentQueryMapper;
    }

    @GetMapping("/poetryLearningContents/{id}")
    public PoetryLearningContentConsoleView findById(@PathVariable BigInteger id) {
        return poetryLearningContentClient.findById(id, PoetryLearningContentConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping("/poetryLearningContents")
    public List<PoetryLearningContentConsoleView> findByIds(@RequestParam List<BigInteger> ids) {
        return poetryLearningContentClient.findByIds(ids, PoetryLearningContentConsoleView.class);
    }

    @GetMapping(value = "/poetryLearningContents/count")
    public long query(PoetryLearningContentConsoleQuery poetryLearningContentConsoleQuery) {
        var query = poetryLearningContentQueryMapper.convert(poetryLearningContentConsoleQuery);
        return poetryLearningContentClient.countByCriteria(query);
    }

    @GetMapping(value = "/poetryLearningContents", params = {"pageNo", "pageSize"})
    public Page<PoetryLearningContentConsoleView> query(PoetryLearningContentConsoleQuery query, Pageable pageable) {
        var criteria = poetryLearningContentQueryMapper.convert(query);
        return poetryLearningContentClient.query(criteria, pageable, PoetryLearningContentConsoleView.class);
    }

    @PostMapping(value = "/poetryLearningContents")
    public String create(@RequestBody PoetryLearningContentConsoleCommand command) {
        var poetryLearningCommand = poetryLearningContentCommandMapper.convert(command);
        return String.valueOf(poetryLearningContentClient.create(poetryLearningCommand));
    }

    @PostMapping(value = "/poetryLearningContents/bulkCreate")
    public int bulkCreate(@RequestBody List<PoetryLearningContentConsoleCommand> commands) {
        var poetryLearningCommands = CollectionUtils.transformToList(commands, poetryLearningContentCommandMapper::convert);
        return poetryLearningContentClient.bulkCreate(poetryLearningCommands);
    }

    @PostMapping("/poetryLearningContents/{subCategoryId}/{poetryGradeId}/generate")
    public List<PoetryLearningContentConsoleCommand> generate(@PathVariable BigInteger subCategoryId, @PathVariable BigInteger poetryGradeId) {
        return poetryLearningContentGenerationConsoleService.generateLearningContentForSubCategoryId(subCategoryId, poetryGradeId);
    }

    @PostMapping("/poetryLearningContents/batchGenerate")
    public List<PoetryLearningContentConsoleCommand> batchGenerateLearningContents(@RequestBody PoetryLeaningContentGenerateCommand  poetryLeaningContentGenerateCommand) {
        return poetryLearningContentGenerationConsoleService.generateLearningContentForAllSubCategoryIds(poetryLeaningContentGenerateCommand.getSubCategoryIds(), poetryLeaningContentGenerateCommand.getPoetryGradeId());
    }

    @PutMapping("/poetryLearningContents/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody PoetryLearningContentConsoleCommand command) {
        var poetryLearningCommand = poetryLearningContentCommandMapper.convert(command);
        poetryLearningContentClient.update(id, poetryLearningCommand);
    }

    @DeleteMapping("/poetryLearningContents/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        poetryLearningContentClient.deleteById(id);
    }
}