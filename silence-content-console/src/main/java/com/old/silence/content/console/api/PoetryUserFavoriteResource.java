package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryUserFavoriteClient;
import com.old.silence.content.console.api.assembler.PoetryUserFavoriteQueryMapper;
import com.old.silence.content.console.dto.PoetryUserFavoriteConsoleQuery;
import com.old.silence.content.console.vo.PoetryUserFavoriteConsoleView;

/**
 * PoetryUserFavorite资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserFavoriteResource {
    private final PoetryUserFavoriteClient poetryUserFavoriteClient;
    private final PoetryUserFavoriteQueryMapper poetryUserFavoriteQueryMapper;

    public PoetryUserFavoriteResource(PoetryUserFavoriteClient poetryUserFavoriteClient,
                                      PoetryUserFavoriteQueryMapper poetryUserFavoriteQueryMapper) {
        this.poetryUserFavoriteClient = poetryUserFavoriteClient;
        this.poetryUserFavoriteQueryMapper = poetryUserFavoriteQueryMapper;
    }

    @GetMapping(value = "/poetryUserFavorites", params = {"pageNo", "pageSize"})
    public Page<PoetryUserFavoriteConsoleView> query(PoetryUserFavoriteConsoleQuery query, Pageable pageable) {
        var criteria = poetryUserFavoriteQueryMapper.convert(query);
        return poetryUserFavoriteClient.query(criteria, pageable, PoetryUserFavoriteConsoleView.class);
    }

}