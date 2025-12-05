package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.PoetryUserLearningStatsMapper;
import com.old.silence.content.api.dto.PoetryUserLearningStatsCommand;
import com.old.silence.content.api.dto.PoetryUserLearningStatsQuery;
import com.old.silence.content.domain.model.poetry.PoetryUserLearningStats;
import com.old.silence.content.domain.repository.poetry.PoetryUserLearningStatsRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * PoetryUserLearningStats资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserLearningStatsResource implements PoetryUserLearningStatsService {
    private final PoetryUserLearningStatsRepository poetryUserLearningStatsRepository;
    private final PoetryUserLearningStatsMapper poetryUserLearningStatsMapper;

    public PoetryUserLearningStatsResource(PoetryUserLearningStatsRepository poetryUserLearningStatsRepository,
                                           PoetryUserLearningStatsMapper poetryUserLearningStatsMapper) {
        this.poetryUserLearningStatsRepository = poetryUserLearningStatsRepository;
        this.poetryUserLearningStatsMapper = poetryUserLearningStatsMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryUserLearningStatsRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(PoetryUserLearningStatsQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, PoetryUserLearningStats.class);
        return poetryUserLearningStatsRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(PoetryUserLearningStatsCommand command) {
        var poetryUserLearningStats = poetryUserLearningStatsMapper.convert(command);
        poetryUserLearningStatsRepository.create(poetryUserLearningStats);
        return poetryUserLearningStats.getId();
    }

    @Override
    public void update(BigInteger id, PoetryUserLearningStatsCommand command) {
        var poetryUserLearningStats = poetryUserLearningStatsMapper.convert(command);
        poetryUserLearningStats.setId(id);
        validateModifyingResult(poetryUserLearningStatsRepository.update(poetryUserLearningStats));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(poetryUserLearningStatsRepository.deleteById(id));
    }
}