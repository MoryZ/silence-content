package com.old.silence.content.application.api;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.old.silence.content.application.api.vo.PoetryUserLearningStatsApplicationView;
import com.old.silence.web.data.ProjectedPayloadType;

/**
 * PoetryUserLearningStats服务接口
 */
interface PoetryUserLearningStatsApplicationService {

    @GetMapping(value = "/poetryUserLearningStats/{userId}")
    <T> Optional<T> findByUserId(@PathVariable BigInteger userId, @ProjectedPayloadType(PoetryUserLearningStatsApplicationView.class) Class<T> projectionType);

}