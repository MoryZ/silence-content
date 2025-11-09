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
import com.old.silence.content.api.dto.PoetryCategoryCommand;
import com.old.silence.content.api.dto.PoetryCategoryQuery;
import com.old.silence.content.api.vo.PoetryCategoryView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * PoetryCategory服务接口
 */
interface PoetryCategoryService {

    @GetMapping(value = "/poetryCategories/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(PoetryCategoryView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryCategories")
    <T> List<T> findByIds(@RequestParam List<BigInteger> ids, @ProjectedPayloadType(PoetryCategoryView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryCategories/{parentId}/children")
    <T> List<T> findByParentId(@PathVariable BigInteger parentId, @ProjectedPayloadType(PoetryCategoryView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryCategories", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryCategoryQuery query, Pageable pageable,
                      @ProjectedPayloadType(PoetryCategoryView.class) Class<T> projectionType);

    @PostJsonMapping("/poetryCategories")
    BigInteger create(@RequestBody @Validated PoetryCategoryCommand command);

    @PutJsonMapping(value = "/poetryCategories/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PoetryCategoryCommand command);

    @DeleteMapping("/poetryCategories/{id}")
    void deleteById(@PathVariable BigInteger id);

}