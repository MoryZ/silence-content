package com.old.silence.content.application.api;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.old.silence.content.application.api.dto.PoetryDailyStudyPlanApplicationCommand;
import com.old.silence.content.application.api.dto.PoetryDailyStudyPlanApplicationQuery;
import com.old.silence.content.application.api.vo.PoetryDailyStudyPlanApplicationView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * PoetryDailyStudyPlan服务接口
 */
interface PoetryDailyStudyPlanApplicationService {

    @GetMapping(value = "/poetryDailyStudyPlans/{userId}")
    <T> Optional<T> findByUserIdAndSubCategoryIdAndPlanDate(@PathVariable BigInteger userId, @RequestParam BigInteger subCategoryId, @RequestParam LocalDate planDate,
                                                            @ProjectedPayloadType(PoetryDailyStudyPlanApplicationView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryDailyStudyPlans", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryDailyStudyPlanApplicationQuery query, Pageable pageable,
                      @ProjectedPayloadType(PoetryDailyStudyPlanApplicationView.class) Class<T> projectionType);

    @PostJsonMapping("/poetryDailyStudyPlans")
    BigInteger create(@RequestBody @Validated PoetryDailyStudyPlanApplicationCommand command);

    @PutJsonMapping(value = "/poetryDailyStudyPlans/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PoetryDailyStudyPlanApplicationCommand command);


}