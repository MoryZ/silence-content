package com.old.silence.content.application.api;


import java.math.BigInteger;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryUserLearningStatsClient;
import com.old.silence.content.api.PoetryUserStudyNoteClient;
import com.old.silence.content.api.dto.PoetryUserStudyNoteCommand;
import com.old.silence.content.application.api.assembler.PoetryUserStudyNoteApplicationMapper;
import com.old.silence.content.application.api.dto.PoetryUserStudyNoteApplicationCommand;


/**
 * PoetryUserStudyNote资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserStudyNoteResource implements PoetryUserStudyNoteApplicationService {


    private final PoetryUserStudyNoteClient poetryUserStudyNoteClient;
    private final PoetryUserStudyNoteApplicationMapper poetryUserStudyNoteApplicationMapper;

    public PoetryUserStudyNoteResource(PoetryUserStudyNoteClient poetryUserStudyNoteClient,
                                       PoetryUserStudyNoteApplicationMapper poetryUserStudyNoteApplicationMapper) {
        this.poetryUserStudyNoteClient = poetryUserStudyNoteClient;
        this.poetryUserStudyNoteApplicationMapper = poetryUserStudyNoteApplicationMapper;
    }


    @Override
    public <T> List<T> findByContentIdAndUserId(BigInteger contentId, BigInteger userId, Class<T> projectionType) {
        return poetryUserStudyNoteClient.findByContentIdAndUserId(contentId, userId, projectionType);
    }

    @Override
    public BigInteger create(PoetryUserStudyNoteApplicationCommand command) {
        var poetryUserStudyNoteCommand = poetryUserStudyNoteApplicationMapper.convert(command);
        return poetryUserStudyNoteClient.create(poetryUserStudyNoteCommand);
    }

    @Override
    public void update(BigInteger id, PoetryUserStudyNoteApplicationCommand command) {
        var poetryUserStudyNoteCommand = poetryUserStudyNoteApplicationMapper.convert(command);
        poetryUserStudyNoteClient.update(id, poetryUserStudyNoteCommand);
    }

    @Override
    public void deleteById(BigInteger id) {
        poetryUserStudyNoteClient.deleteById(id);
    }
}