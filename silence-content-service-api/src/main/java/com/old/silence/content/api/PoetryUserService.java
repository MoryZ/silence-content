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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.old.silence.content.api.dto.PoetryUserCommand;
import com.old.silence.content.api.dto.PoetryUserQuery;
import com.old.silence.content.api.vo.PoetryUserView;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * PoetryUser服务接口
 */
interface PoetryUserService {

    @GetMapping(value = "/poetryUsers")
    <T> Optional<T> findByOpenid(@RequestParam String openid, @ProjectedPayloadType(PoetryUserView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryUsers/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(PoetryUserView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryUsers", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryUserQuery query, Pageable pageable,
                      @ProjectedPayloadType(PoetryUserView.class) Class<T> projectionType);

    @PostMapping("/poetryUsers")
    BigInteger create(@RequestBody @Validated PoetryUserCommand command);

    @PutMapping(value = "/poetryUsers/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PoetryUserCommand command);

    @PutMapping(value = "/poetryUsers/{id}/bindPhone")
    void bindPhone(@PathVariable BigInteger id, @RequestParam String phone);

    @DeleteMapping("/poetryUsers/{id}")
    void deleteById(@PathVariable BigInteger id);


}