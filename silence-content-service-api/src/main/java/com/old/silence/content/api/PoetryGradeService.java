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
import com.old.silence.content.api.dto.PoetryGradeCommand;
import com.old.silence.content.api.dto.PoetryGradeQuery;
import com.old.silence.content.api.vo.PoetryGradeView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * PoetryGrade服务接口
 */
interface PoetryGradeService {

    @GetMapping(value = "/poetryGrades/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(PoetryGradeView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryGrades", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryGradeQuery query, Pageable pageable,
                      @ProjectedPayloadType(PoetryGradeView.class) Class<T> projectionType);

    @PostJsonMapping("/poetryGrades")
    BigInteger create(@RequestBody @Validated PoetryGradeCommand command);

    @PutJsonMapping(value = "/poetryGrades/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PoetryGradeCommand command);

    @DeleteMapping("/poetryGrades/{id}")
    void deleteById(@PathVariable BigInteger id);
}