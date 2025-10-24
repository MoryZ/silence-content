package com.old.silence.content.api;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.PoetryUserLearningStatsCommand;
import com.old.silence.content.api.dto.PoetryUserLearningStatsQuery;
import com.old.silence.content.api.vo.PoetryUserLearningStatsView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * PoetryUserLearningStats服务接口
 */
interface PoetryUserLearningStatsService {

    @GetMapping(value = "/poetryUserLearningStats/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(PoetryUserLearningStatsView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryUserLearningStats", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryUserLearningStatsQuery query, Pageable pageable,
                      @ProjectedPayloadType(PoetryUserLearningStatsView.class) Class<T> projectionType);

    @PostJsonMapping("/poetryUserLearningStats")
    BigInteger create(@RequestBody @Validated PoetryUserLearningStatsCommand command);

    @PutJsonMapping(value = "/poetryUserLearningStats/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PoetryUserLearningStatsCommand command);

    @DeleteMapping("/poetryUserLearningStats/{id}")
    void deleteById(@PathVariable BigInteger id);
}