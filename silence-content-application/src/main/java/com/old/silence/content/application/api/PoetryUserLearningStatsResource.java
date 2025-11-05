package com.old.silence.content.application.api;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryUserLearningStatsClient;

/**
 * PoetryUserLearningStats资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserLearningStatsResource implements PoetryUserLearningStatsApplicationService {

    private final PoetryUserLearningStatsClient poetryUserLearningStatsClient;

    public PoetryUserLearningStatsResource(PoetryUserLearningStatsClient poetryUserLearningStatsClient) {
        this.poetryUserLearningStatsClient = poetryUserLearningStatsClient;
    }

    @Override
    public <T> Optional<T> findByUserId(BigInteger userId, Class<T> projectionType) {
        return poetryUserLearningStatsClient.findById(userId, projectionType);
    }
}