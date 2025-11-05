package com.old.silence.content.application.api;

import java.math.BigInteger;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.application.api.dto.PoetryAnswerRecordsApplicationCommand;
import com.old.silence.content.application.api.dto.PoetryAnswerRecordsApplicationQuery;
import com.old.silence.content.application.api.vo.PoetryAnswerRecordsApplicationView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

/**
* PoetryAnswerRecords服务接口
*/
interface PoetryAnswerRecordsApplicationService {

    @GetMapping(value = "/poetryAnswerRecords", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryAnswerRecordsApplicationQuery query, Pageable pageable,
                      @ProjectedPayloadType(PoetryAnswerRecordsApplicationView.class) Class<T> projectionType);

    @PostJsonMapping("/poetryAnswerRecords")
    BigInteger create(@RequestBody @Validated PoetryAnswerRecordsApplicationCommand command);

}