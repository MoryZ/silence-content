package com.old.silence.content.application.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import com.old.silence.content.application.api.dto.PoetryGradeApplicationQuery;
import com.old.silence.content.application.api.vo.PoetryGradeApplicationView;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * PoetryGrade服务接口
 */
interface PoetryGradeApplicationService {


    @GetMapping(value = "/poetryGrades", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryGradeApplicationQuery query, Pageable pageable,
                      @ProjectedPayloadType(PoetryGradeApplicationView.class) Class<T> projectionType);

}