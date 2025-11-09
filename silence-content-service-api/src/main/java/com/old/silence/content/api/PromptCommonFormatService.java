package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.old.silence.content.api.dto.PromptCommonFormatCommand;
import com.old.silence.content.api.dto.PromptCommonFormatQuery;
import com.old.silence.content.api.vo.PromptCommonFormatView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
* PromptCommonFormat服务接口
*/
interface PromptCommonFormatService {

    @GetMapping(value = "/promptCommonFormats/{id}")
    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(PromptCommonFormatView.class) Class<T> projectionType);

    @GetMapping(value = "/promptCommonFormats")
    <T> Optional<T> findByActive(@RequestParam Boolean active, @ProjectedPayloadType(PromptCommonFormatView.class) Class<T> projectionType);

    @GetMapping(value = "/promptCommonFormats", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PromptCommonFormatQuery query, Pageable pageable,
                        @ProjectedPayloadType(PromptCommonFormatView.class) Class<T> projectionType);

    @PostJsonMapping("/promptCommonFormats")
    BigInteger create(@RequestBody @Validated PromptCommonFormatCommand command);

    @PutJsonMapping(value = "/promptCommonFormats/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PromptCommonFormatCommand command);

    @DeleteMapping("/promptCommonFormats/{id}")
    void deleteById(@PathVariable BigInteger id);

}