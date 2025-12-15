package com.old.silence.content.api;


import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.old.silence.content.api.dto.CodeFileSpecCommand;
import com.old.silence.content.api.dto.CodeFileSpecQuery;
import com.old.silence.content.api.vo.CodeFileSpecView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
* CodeFileSpec服务接口
*/
interface CodeFileSpecService {

    @GetMapping(value = "/codeFileSpecs/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(CodeFileSpecView.class) Class<T> projectionType);

    @GetMapping(value = "/codeFileSpecs", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap CodeFileSpecQuery query, Pageable pageable,
                      @ProjectedPayloadType(CodeFileSpecView.class) Class<T> projectionType);

    @PostJsonMapping("/codeFileSpecs")
    BigInteger create(@RequestBody @Validated CodeFileSpecCommand command);

    @PutJsonMapping(value = "/codeFileSpecs/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated CodeFileSpecCommand command);

    @DeleteMapping("/codeFileSpecs/{id}")
    void deleteById(@PathVariable BigInteger id);
}