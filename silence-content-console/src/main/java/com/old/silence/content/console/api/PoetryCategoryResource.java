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
import com.old.silence.content.api.PoetryCategoryClient;
import com.old.silence.content.console.api.assembler.PoetryCategoryCommandMapper;
import com.old.silence.content.console.api.assembler.PoetryCategoryQueryMapper;
import com.old.silence.content.console.dto.PoetryCategoryConsoleCommand;
import com.old.silence.content.console.dto.PoetryCategoryConsoleQuery;
import com.old.silence.content.console.vo.PoetryCategoryConsoleView;
import com.old.silence.content.console.vo.TreeVo;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.core.util.CollectionUtils;

import java.math.BigInteger;
import java.util.List;

/**
 * PoetryCategory资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryCategoryResource {
    private final PoetryCategoryClient poetryCategoryClient;
    private final PoetryCategoryCommandMapper poetryCategoryMapper;
    private final PoetryCategoryQueryMapper poetryCategoryQueryMapper;

    public PoetryCategoryResource(PoetryCategoryClient poetryCategoryClient,
                                  PoetryCategoryCommandMapper poetryCategoryMapper,
                                  PoetryCategoryQueryMapper poetryCategoryQueryMapper) {
        this.poetryCategoryClient = poetryCategoryClient;
        this.poetryCategoryMapper = poetryCategoryMapper;
        this.poetryCategoryQueryMapper = poetryCategoryQueryMapper;
    }

    @GetMapping("/poetryCategories/{parentId}/children")
    public List<TreeVo> findByParentId(@PathVariable BigInteger parentId) {
        List<PoetryCategoryConsoleView> poetryCategories = poetryCategoryClient.findByParentId(parentId, PoetryCategoryConsoleView.class);
        return CollectionUtils.transformToList(poetryCategories, poetryCategory ->
                new TreeVo(poetryCategory.getId(), poetryCategory.getName()));
    }

    @GetMapping("/poetryCategories/{id}")
    public PoetryCategoryConsoleView findById(@PathVariable BigInteger id) {
        return poetryCategoryClient.findById(id, PoetryCategoryConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/poetryCategories", params = {"pageNo", "pageSize"})
    public Page<PoetryCategoryConsoleView> query(PoetryCategoryConsoleQuery query, Pageable pageable) {
        var poetryCategoryQuery = poetryCategoryQueryMapper.convert(query);
        return poetryCategoryClient.query(poetryCategoryQuery, pageable, PoetryCategoryConsoleView.class);
    }

    @PostMapping(value = "/poetryCategories")
    public String create(@RequestBody PoetryCategoryConsoleCommand command) {
        var poetryCategoryCommand = poetryCategoryMapper.convert(command);
        return String.valueOf(poetryCategoryClient.create(poetryCategoryCommand));
    }

    @PutMapping("/poetryCategories/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody PoetryCategoryConsoleCommand command) {
        var poetryCategoryCommand = poetryCategoryMapper.convert(command);
        poetryCategoryClient.update(id, poetryCategoryCommand);
    }

    @DeleteMapping("/poetryCategories/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        poetryCategoryClient.deleteById(id);
    }
}