package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.PoetryQuizQuestionsMapper;
import com.old.silence.content.api.dto.PoetryQuizQuestionsCommand;
import com.old.silence.content.api.dto.PoetryQuizQuestionsQuery;
import com.old.silence.content.domain.model.poetry.PoetryQuizQuestions;
import com.old.silence.content.domain.repository.poetry.PoetryQuizQuestionsRepository;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * PoetryQuizQuestions资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryQuizQuestionsResource implements PoetryQuizQuestionsService {
    private final PoetryQuizQuestionsRepository poetryQuizQuestionsRepository;
    private final PoetryQuizQuestionsMapper poetryQuizQuestionsMapper;


    public PoetryQuizQuestionsResource(PoetryQuizQuestionsRepository poetryQuizQuestionsRepository,
                                       PoetryQuizQuestionsMapper poetryQuizQuestionsMapper) {
        this.poetryQuizQuestionsRepository = poetryQuizQuestionsRepository;
        this.poetryQuizQuestionsMapper = poetryQuizQuestionsMapper;

    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryQuizQuestionsRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(PoetryQuizQuestionsQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, PoetryQuizQuestions.class);
        return poetryQuizQuestionsRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int bulkCreate(List<PoetryQuizQuestionsCommand> commands) {
        var poetryQuizQuestions = CollectionUtils.transformToList(commands, poetryQuizQuestionsMapper::convert);
        return poetryQuizQuestionsRepository.bulkCreate(poetryQuizQuestions);
    }

    @Override
    public void update(BigInteger id, PoetryQuizQuestionsCommand command) {
        var poetryQuizQuestions = poetryQuizQuestionsMapper.convert(command);
        poetryQuizQuestions.setId(id);
        validateModifyingResult(poetryQuizQuestionsRepository.update(poetryQuizQuestions));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(poetryQuizQuestionsRepository.deleteById(id));
    }

}