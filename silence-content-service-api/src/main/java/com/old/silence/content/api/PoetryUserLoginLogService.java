package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.PoetryUserLoginLogCommand;
import com.old.silence.content.api.dto.PoetryUserLoginLogQuery;
import com.old.silence.content.api.vo.PoetryUserLoginLogView;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
 * PoetryUser服务接口
 */
interface PoetryUserLoginLogService {

    @GetMapping(value = "/poetryUserLoginLogs/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(PoetryUserLoginLogView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryUserLoginLogs", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryUserLoginLogQuery query, Pageable pageable,
                      @ProjectedPayloadType(PoetryUserLoginLogView.class) Class<T> projectionType);

    @PostMapping("/poetryUserLoginLogs")
    BigInteger create(@RequestBody @Validated PoetryUserLoginLogCommand command);

    @PutMapping(value = "/poetryUserLoginLogs/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PoetryUserLoginLogCommand command);

    @DeleteMapping("/poetryUserLoginLogs/{id}")
    void deleteById(@PathVariable BigInteger id);


}