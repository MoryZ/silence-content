package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.old.silence.content.api.dto.PoetryDailyStudyPlanCommand;
import com.old.silence.content.api.dto.PoetryDailyStudyPlanQuery;
import com.old.silence.content.api.vo.PoetryDailyStudyPlanView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
* PoetryDailyStudyPlan服务接口
*/
interface PoetryDailyStudyPlanService {

        @GetMapping(value = "/poetryDailyStudyPlans/{id}")
        <T> Optional<T>findById(@PathVariable BigInteger id, @ProjectedPayloadType(PoetryDailyStudyPlanView.class) Class<T> projectionType);

        @GetMapping(value = "/poetryDailyStudyPlans", params = {"pageNo", "pageSize"})
        <T> Page<T> query(@Validated @SpringQueryMap PoetryDailyStudyPlanQuery query, Pageable pageable,
                            @ProjectedPayloadType(PoetryDailyStudyPlanView.class) Class<T> projectionType);

        @PostJsonMapping("/poetryDailyStudyPlans")
        BigInteger create(@RequestBody @Validated PoetryDailyStudyPlanCommand command);

        @PutJsonMapping(value = "/poetryDailyStudyPlans/{id}")
        void update(@PathVariable BigInteger id, @RequestBody @Validated PoetryDailyStudyPlanCommand command);

        @DeleteMapping("/poetryDailyStudyPlans/{id}")
        void deleteById(@PathVariable BigInteger id);
}