package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.old.silence.content.api.dto.PoetryAnswerRecordsCommand;
import com.old.silence.content.api.dto.PoetryAnswerRecordsQuery;
import com.old.silence.content.api.vo.PoetryAnswerRecordsView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.List;

/**
* PoetryAnswerRecords服务接口
*/
interface PoetryAnswerRecordsService {

    @GetMapping(value = "/poetryAnswerRecords/{contentId}/{subCategoryId}/{userId}")
    <T> List<T> findByContentIdAndSubCategoryIdAndUserId(@PathVariable BigInteger contentId, @PathVariable BigInteger subCategoryId,
                                                         @PathVariable BigInteger userId, @ProjectedPayloadType(PoetryAnswerRecordsView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryAnswerRecords", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryAnswerRecordsQuery query, Pageable pageable,
                        @ProjectedPayloadType(PoetryAnswerRecordsView.class) Class<T> projectionType);

    @PostJsonMapping("/poetryAnswerRecords")
    BigInteger create(@RequestBody @Validated PoetryAnswerRecordsCommand command);

    @PutJsonMapping(value = "/poetryAnswerRecords/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PoetryAnswerRecordsCommand command);

    @DeleteMapping("/poetryAnswerRecords/{id}")
    void deleteById(@PathVariable BigInteger id);
}