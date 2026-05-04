package com.old.silence.content.api;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.PoetryUserStudySettingCommand;
import com.old.silence.content.api.dto.PoetryUserStudySettingQuery;
import com.old.silence.content.api.vo.PoetryUserStudySettingView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

import java.math.BigInteger;
import java.util.Optional;

/**
 * PoetryUserStudySetting服务接口
 */
interface PoetryUserStudySettingService {

    @GetMapping(value = "/poetryUserStudySettings/{subCategoryId}/{gradeId}/{userId}")
    <T> Optional<T> findBySubCategoryIdGradeIdAndUserId(@PathVariable BigInteger subCategoryId, @PathVariable BigInteger gradeId, @PathVariable BigInteger userId,
                                                        @ProjectedPayloadType(PoetryUserStudySettingView.class) Class<T> projectionType);

    @GetMapping(value = "/poetryUserStudySettings", params = {"pageNo", "pageSize"})
    <T> Page<T> query(@Validated @SpringQueryMap PoetryUserStudySettingQuery query, Pageable pageable,
                      @ProjectedPayloadType(PoetryUserStudySettingView.class) Class<T> projectionType);

    @PostJsonMapping("/poetryUserStudySettings/{userId}/{subCategoryId}/generateTodayPlan")
    void generateTodayPlan(@PathVariable BigInteger userId, @PathVariable BigInteger subCategoryId);

    @PostJsonMapping("/poetryUserStudySettings")
    BigInteger create(@RequestBody @Validated PoetryUserStudySettingCommand command);

    @PutJsonMapping(value = "/poetryUserStudySettings/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PoetryUserStudySettingCommand command);

    @DeleteMapping("/poetryUserStudySettings/{id}")
    void deleteById(@PathVariable BigInteger id);
}