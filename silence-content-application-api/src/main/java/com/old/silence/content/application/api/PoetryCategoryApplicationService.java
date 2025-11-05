package com.old.silence.content.application.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import com.old.silence.content.application.api.dto.PoetryCategoryApplicationQuery;
import com.old.silence.content.application.api.vo.PoetryCategoryApplicationView;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * PoetryCategory服务接口
 */
interface PoetryCategoryApplicationService {


    @GetMapping(value = "/poetryCategories", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryCategoryApplicationQuery query, Pageable pageable,
                      @ProjectedPayloadType(PoetryCategoryApplicationView.class) Class<T> projectionType);


}