package com.old.silence.content.application.api;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.application.api.dto.PoetryUserStudySettingApplicationCommand;
import com.old.silence.content.application.api.vo.PoetryUserStudySettingApplicationView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * PoetryUserStudySetting服务接口
 */
interface PoetryUserStudySettingApplicationService {

    @GetMapping(value = "/poetryUserStudySettings/{subCategoryId}/{gradeId}/{userId}")
    <T> Optional<T> findBySubCategoryIdGradeIdAndUserId(@PathVariable BigInteger subCategoryId, @PathVariable BigInteger gradeId, @PathVariable BigInteger userId,
                                                        @ProjectedPayloadType(PoetryUserStudySettingApplicationView.class) Class<T> projectionType);

    @PostJsonMapping("/poetryUserStudySettings")
    BigInteger create(@RequestBody @Validated PoetryUserStudySettingApplicationCommand command);

    @PutJsonMapping(value = "/poetryUserStudySettings/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PoetryUserStudySettingApplicationCommand command);

}