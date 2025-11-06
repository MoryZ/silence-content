package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.old.silence.content.api.dto.PoetryQuizQuestionsCommand;
import com.old.silence.content.api.dto.PoetryQuizQuestionsQuery;
import com.old.silence.content.api.vo.PoetryQuizQuestionsView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

/**
* PoetryQuizQuestions服务接口
*/
interface PoetryQuizQuestionsService {

    @GetMapping(value = "/poetryQuizQuestions/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(PoetryQuizQuestionsView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryQuizQuestions", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryQuizQuestionsQuery query, Pageable pageable,
                        @ProjectedPayloadType(PoetryQuizQuestionsView.class) Class<T> projectionType);

    @PostJsonMapping("/poetryQuizQuestions")
    BigInteger create(@RequestBody @Validated PoetryQuizQuestionsCommand command);

    @PutJsonMapping(value = "/poetryQuizQuestions/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PoetryQuizQuestionsCommand command);

    @PostMapping("/poetryQuizQuestions/batch-generate")
    void batchGenerateQuestions(Pageable pageable);

    @PostMapping(value = "/poetryQuizQuestions/generate/{contentId}")
    void generateQuestionsForContent(@PathVariable BigInteger contentId);

    @DeleteMapping("/poetryQuizQuestions/{id}")
    void deleteById(@PathVariable BigInteger id);
}