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
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryUserFavoriteClient;
import com.old.silence.content.console.api.assembler.PoetryUserFavoriteCommandMapper;
import com.old.silence.content.console.api.assembler.PoetryUserFavoriteQueryMapper;
import com.old.silence.content.console.dto.PoetryUserFavoriteConsoleCommand;
import com.old.silence.content.console.dto.PoetryUserFavoriteConsoleQuery;
import com.old.silence.content.console.vo.PoetryUserFavoriteConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;

import java.math.BigInteger;

/**
 * PoetryUserFavorite资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserFavoriteResource {
    private final PoetryUserFavoriteClient poetryUserFavoriteClient;
    private final PoetryUserFavoriteCommandMapper poetryUserFavoriteCommandMapper;
    private final PoetryUserFavoriteQueryMapper poetryUserFavoriteQueryMapper;

    public PoetryUserFavoriteResource(PoetryUserFavoriteClient poetryUserFavoriteClient,
                                      PoetryUserFavoriteCommandMapper poetryUserFavoriteCommandMapper,
                                      PoetryUserFavoriteQueryMapper poetryUserFavoriteQueryMapper) {
        this.poetryUserFavoriteClient = poetryUserFavoriteClient;
        this.poetryUserFavoriteCommandMapper = poetryUserFavoriteCommandMapper;
        this.poetryUserFavoriteQueryMapper = poetryUserFavoriteQueryMapper;
    }

    @GetMapping("/poetryUserFavorites/{id}")
    public PoetryUserFavoriteConsoleView findById(@PathVariable BigInteger id) {
        return poetryUserFavoriteClient.findById(id, PoetryUserFavoriteConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/poetryUserFavorites", params = {"pageNo", "pageSize"})
    public Page<PoetryUserFavoriteConsoleView> query(PoetryUserFavoriteConsoleQuery query, Pageable pageable) {
        var criteria = poetryUserFavoriteQueryMapper.convert(query);
        return poetryUserFavoriteClient.query(criteria, pageable, PoetryUserFavoriteConsoleView.class);
    }

    @PostMapping(value = "/poetryUserFavorites")
    public String create(@RequestBody PoetryUserFavoriteConsoleCommand command) {
        var poetryUserFavoriteCommand = poetryUserFavoriteCommandMapper.convert(command);
        return String.valueOf(poetryUserFavoriteClient.create(poetryUserFavoriteCommand));
    }

    @PutMapping("/poetryUserFavorites/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody PoetryUserFavoriteConsoleCommand command) {
        var poetryUserFavoriteCommand = poetryUserFavoriteCommandMapper.convert(command);
        poetryUserFavoriteClient.update(id, poetryUserFavoriteCommand);
    }

    @DeleteMapping("/poetryUserFavorites/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        poetryUserFavoriteClient.deleteById(id);
    }
}