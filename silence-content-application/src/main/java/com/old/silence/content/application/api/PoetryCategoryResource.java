package com.old.silence.content.application.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.PoetryCategoryClient;
import com.old.silence.content.application.api.assembler.PoetryCategoryApplicationQueryMapper;
import com.old.silence.content.application.api.dto.PoetryCategoryApplicationQuery;

/**
 * PoetryCategory资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryCategoryResource implements PoetryCategoryApplicationService {

    private final PoetryCategoryClient poetryCategoryClient;
    private final PoetryCategoryApplicationQueryMapper poetryCategoryApplicationQueryMapper;

    public PoetryCategoryResource(PoetryCategoryClient poetryCategoryClient,
                                  PoetryCategoryApplicationQueryMapper poetryCategoryApplicationQueryMapper) {
        this.poetryCategoryClient = poetryCategoryClient;
        this.poetryCategoryApplicationQueryMapper = poetryCategoryApplicationQueryMapper;
    }

    @Override
    public <T> Page<T> query(PoetryCategoryApplicationQuery query, Pageable pageable, Class<T> projectionType) {
        var poetryCategoryQuery = poetryCategoryApplicationQueryMapper.convert(query);
        return poetryCategoryClient.query(poetryCategoryQuery, pageable, projectionType);
    }
}