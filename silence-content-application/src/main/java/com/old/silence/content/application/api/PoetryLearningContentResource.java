package com.old.silence.content.application.api;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryLearningContentClient;
import com.old.silence.content.application.api.assembler.PoetryLearningContentApplicationQueryMapper;
import com.old.silence.content.application.api.dto.PoetryLearningContentApplicationQuery;


/**
 * PoetryLearningContent资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryLearningContentResource implements PoetryLearningContentApplicationService {

    private final PoetryLearningContentClient poetryLearningContentClient;
    private final PoetryLearningContentApplicationQueryMapper poetryLearningContentApplicationQueryMapper;

    public PoetryLearningContentResource(PoetryLearningContentClient poetryLearningContentClient,
                                         PoetryLearningContentApplicationQueryMapper poetryLearningContentApplicationQueryMapper) {
        this.poetryLearningContentClient = poetryLearningContentClient;
        this.poetryLearningContentApplicationQueryMapper = poetryLearningContentApplicationQueryMapper;
    }


    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryLearningContentClient.findById(id, projectionType);
    }

    @Override
    public <T> List<T> findByIds(List<BigInteger> ids, Class<T> projectionType) {
        return poetryLearningContentClient.findByIds(ids, projectionType);
    }

    @Override
    public long countByCriteria(PoetryLearningContentApplicationQuery query) {
        var poetryLearningContentQuery = poetryLearningContentApplicationQueryMapper.convert(query);
        return poetryLearningContentClient.countByCriteria(poetryLearningContentQuery);
    }

    @Override
    public <T> Page<T> query(PoetryLearningContentApplicationQuery query, Pageable pageable, Class<T> projectionType) {
        var poetryLearningContentQuery = poetryLearningContentApplicationQueryMapper.convert(query);
        return poetryLearningContentClient.query(poetryLearningContentQuery, pageable, projectionType);
    }
}