package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.old.silence.content.api.dto.PoetryLearningContentCommand;
import com.old.silence.content.api.dto.PoetryLearningContentQuery;
import com.old.silence.content.api.vo.PoetryLearningContentView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
* PoetryLearningContent服务接口
*/
interface PoetryLearningContentService {

        @GetMapping(value = "/poetryLearningContents/{id}")
        <T> Optional<T>findById(@PathVariable BigInteger id, @ProjectedPayloadType(PoetryLearningContentView.class) Class<T> projectionType);

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