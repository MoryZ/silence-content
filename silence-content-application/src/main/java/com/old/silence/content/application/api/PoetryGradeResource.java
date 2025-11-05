package com.old.silence.content.application.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryGradeClient;
import com.old.silence.content.application.api.assembler.PoetryGradeApplicationQueryMapper;
import com.old.silence.content.application.api.dto.PoetryGradeApplicationQuery;

/**
 * PoetryGrade资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryGradeResource implements PoetryGradeApplicationService {

    private final PoetryGradeClient poetryGradeClient;
    private final PoetryGradeApplicationQueryMapper poetryGradeApplicationQueryMapper;

    public PoetryGradeResource(PoetryGradeClient poetryGradeClient,
                               PoetryGradeApplicationQueryMapper poetryGradeApplicationQueryMapper) {
        this.poetryGradeClient = poetryGradeClient;
        this.poetryGradeApplicationQueryMapper = poetryGradeApplicationQueryMapper;
    }

    @Override
    public <T> Page<T> query(PoetryGradeApplicationQuery query, Pageable pageable, Class<T> projectionType) {
        var poetryGradeQuery = poetryGradeApplicationQueryMapper.convert(query);
        return poetryGradeClient.query(poetryGradeQuery, pageable, projectionType);
    }
}