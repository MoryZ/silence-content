package com.old.silence.content.application.api;

import java.math.BigInteger;
import java.util.Optional;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.PoetryUserLearningRecordClient;
import com.old.silence.content.application.api.assembler.PoetryUserLearningRecordApplicationMapper;
import com.old.silence.content.application.api.dto.PoetryUserLearningRecordApplicationCommand;


/**
 * PoetryUserLearningRecord资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserLearningRecordResource implements PoetryUserLearningRecordApplicationService {

    private final PoetryUserLearningRecordClient poetryUserLearningRecordClient;
    private final PoetryUserLearningRecordApplicationMapper poetryUserLearningRecordApplicationMapper;

    public PoetryUserLearningRecordResource(PoetryUserLearningRecordClient poetryUserLearningRecordClient,
                                            PoetryUserLearningRecordApplicationMapper poetryUserLearningRecordApplicationMapper) {
        this.poetryUserLearningRecordClient = poetryUserLearningRecordClient;
        this.poetryUserLearningRecordApplicationMapper = poetryUserLearningRecordApplicationMapper;
    }

    @Override
    public <T> Optional<T> findByUserId(BigInteger userId, Class<T> projectionType) {
        return poetryUserLearningRecordClient.findById(userId, projectionType);
    }

    @Override
    public BigInteger create(PoetryUserLearningRecordApplicationCommand command) {
        var poetryUserLearningRecordCommand = poetryUserLearningRecordApplicationMapper.convert(command);
        return poetryUserLearningRecordClient.create(poetryUserLearningRecordCommand);
    }

    @Override
    public void update(BigInteger id, PoetryUserLearningRecordApplicationCommand command) {
        var poetryUserLearningRecordCommand = poetryUserLearningRecordApplicationMapper.convert(command);
        poetryUserLearningRecordClient.update(id, poetryUserLearningRecordCommand);
    }
}