package com.old.silence.content.application.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import com.old.silence.content.application.api.dto.PoetryQuizQuestionsApplicationQuery;
import com.old.silence.content.application.api.vo.PoetryQuizQuestionsApplicationView;
import com.old.silence.web.data.ProjectedPayloadType;

/**
* PoetryQuizQuestions服务接口
*/
interface PoetryQuizQuestionsApplicationService {

    @GetMapping(value = "/poetryQuizQuestions", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryQuizQuestionsApplicationQuery query, Pageable pageable,
                      @ProjectedPayloadType(PoetryQuizQuestionsApplicationView.class) Class<T> projectionType);

}