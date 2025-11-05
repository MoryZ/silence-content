package com.old.silence.content.application.api;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.old.silence.content.application.api.dto.PoetryLearningContentApplicationQuery;
import com.old.silence.content.application.api.vo.PoetryLearningContentApplicationView;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * PoetryLearningContent服务接口
 */
interface PoetryLearningContentApplicationService {

    @GetMapping(value = "/poetryLearningContents/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(PoetryLearningContentApplicationView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryLearningContents")
    <T> List<T> findByIds(@RequestParam List<BigInteger> ids, @ProjectedPayloadType(PoetryLearningContentApplicationView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryLearningContents/count")
    long countByCriteria(@Validated @SpringQueryMap PoetryLearningContentApplicationQuery query);

    @GetMapping(value = "/poetryLearningContents", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryLearningContentApplicationQuery query, Pageable pageable,
                      @ProjectedPayloadType(PoetryLearningContentApplicationView.class) Class<T> projectionType);

}