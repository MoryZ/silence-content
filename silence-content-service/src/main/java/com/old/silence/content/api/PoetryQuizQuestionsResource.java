package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.old.silence.content.api.assembler.PoetryQuizQuestionsMapper;
import com.old.silence.content.api.dto.PoetryQuizQuestionsCommand;
import com.old.silence.content.api.dto.PoetryQuizQuestionsQuery;
import com.old.silence.content.domain.model.PoetryQuizQuestions;
import com.old.silence.content.domain.repository.PoetryQuizQuestionsRepository;
import com.old.silence.content.domain.service.PoetryQuizQuestionGenerationService;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
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
    private final PoetryQuizQuestionGenerationService questionGenerationService;

    public PoetryQuizQuestionsResource(PoetryQuizQuestionsRepository poetryQuizQuestionsRepository,
                                PoetryQuizQuestionsMapper poetryQuizQuestionsMapper,
                                PoetryQuizQuestionGenerationService questionGenerationService) {
        this.poetryQuizQuestionsRepository = poetryQuizQuestionsRepository;
        this.poetryQuizQuestionsMapper = poetryQuizQuestionsMapper;
        this.questionGenerationService = questionGenerationService;
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
    public BigInteger create(PoetryQuizQuestionsCommand command) {
        var poetryQuizQuestions = poetryQuizQuestionsMapper.convert(command);
        poetryQuizQuestionsRepository.create(poetryQuizQuestions);
        return poetryQuizQuestions.getId();
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

    /**
     * 批量生成题目：为所有学习内容生成题目
     */
    @Override
    public void batchGenerateQuestions(Pageable pageable) {
        questionGenerationService.generateQuestionsForAllContents(pageable);
    }

    /**
     * 为指定学习内容生成题目
     * @param contentId 学习内容ID
     */
    @Override
    public void generateQuestionsForContent(BigInteger contentId) {
        questionGenerationService.generateQuestionsForContent(contentId);

    }
}