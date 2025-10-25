package com.old.silence.content.api;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.old.silence.content.api.dto.PoetryLearningContentCommand;
import com.old.silence.content.api.dto.PoetryLearningContentQuery;
import com.old.silence.content.api.vo.PoetryLearningContentView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * PoetryLearningContent服务接口
 */
interface PoetryLearningContentService {

    @GetMapping(value = "/poetryLearningContents/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(PoetryLearningContentView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryLearningContents")
    <T> List<T> findByIds(@RequestParam List<BigInteger> ids, @ProjectedPayloadType(PoetryLearningContentView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryLearningContents/count")
    long countByCriteria(@Validated @SpringQueryMap PoetryLearningContentQuery query);

    @GetMapping(value = "/poetryLearningContents", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryLearningContentQuery query, Pageable pageable,
                      @ProjectedPayloadType(PoetryLearningContentView.class) Class<T> projectionType);

    @PostJsonMapping("/poetryLearningContents")
    BigInteger create(@RequestBody @Validated PoetryLearningContentCommand command);

    @PutJsonMapping(value = "/poetryLearningContents/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PoetryLearningContentCommand command);

    @DeleteMapping("/poetryLearningContents/{id}")
    void deleteById(@PathVariable BigInteger id);


}