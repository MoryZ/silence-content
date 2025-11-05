package com.old.silence.content.application.api;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.application.api.dto.PoetryUserLearningRecordApplicationCommand;
import com.old.silence.content.application.api.vo.PoetryUserLearningRecordApplicationView;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * PoetryUserLearningRecord服务接口
 */
interface PoetryUserLearningRecordApplicationService {

    @GetMapping(value = "/poetryUserLearningRecords/{userId}")
    <T> Optional<T> findByUserId(@PathVariable BigInteger userId, @ProjectedPayloadType(PoetryUserLearningRecordApplicationView.class) Class<T> projectionType);

    @PostJsonMapping("/poetryUserLearningRecords")
    BigInteger create(@RequestBody @Validated PoetryUserLearningRecordApplicationCommand command);

    @PutJsonMapping(value = "/poetryUserLearningRecords/{id}")
    void update(@PathVariable BigInteger id, @RequestBody @Validated PoetryUserLearningRecordApplicationCommand command);

}