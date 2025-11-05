package com.old.silence.content.application.api;

import java.math.BigInteger;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.application.api.dto.PoetryUserFavoriteApplicationCommand;
import com.old.silence.content.application.api.dto.PoetryUserFavoriteApplicationQuery;
import com.old.silence.content.application.api.vo.PoetryUserFavoriteApplicationView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * PoetryUserFavorite服务接口
 */
interface PoetryUserFavoriteApplicationService {

    @GetMapping(value = "/poetryUserFavorites", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryUserFavoriteApplicationQuery query, Pageable pageable,
                      @ProjectedPayloadType(PoetryUserFavoriteApplicationView.class) Class<T> projectionType);

    @PostJsonMapping("/poetryUserFavorites")
    BigInteger create(@RequestBody @Validated PoetryUserFavoriteApplicationCommand command);

    @DeleteMapping("/poetryUserFavorites/{id}")
    void deleteById(@PathVariable BigInteger id);
}