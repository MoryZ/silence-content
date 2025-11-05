package com.old.silence.content.application.api;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryUserStudySettingClient;
import com.old.silence.content.api.dto.PoetryUserStudySettingCommand;
import com.old.silence.content.application.api.assembler.PoetryUserStudySettingApplicationMapper;
import com.old.silence.content.application.api.dto.PoetryUserStudySettingApplicationCommand;


/**
 * PoetryUserStudySetting资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserStudySettingResource implements PoetryUserStudySettingApplicationService {

    private final PoetryUserStudySettingClient poetryUserStudySettingClient;
    private final PoetryUserStudySettingApplicationMapper poetryUserStudySettingApplicationMapper;

    public PoetryUserStudySettingResource(PoetryUserStudySettingClient poetryUserStudySettingClient,
                                          PoetryUserStudySettingApplicationMapper poetryUserStudySettingApplicationMapper) {
        this.poetryUserStudySettingClient = poetryUserStudySettingClient;
        this.poetryUserStudySettingApplicationMapper = poetryUserStudySettingApplicationMapper;
    }

    @Override
    public <T> Optional<T> findBySubCategoryIdGradeIdAndUserId(BigInteger subCategoryId, BigInteger gradeId, BigInteger userId, Class<T> projectionType) {
        return poetryUserStudySettingClient.findBySubCategoryIdGradeIdAndUserId(subCategoryId, gradeId, userId, projectionType);
    }

    @Override
    public BigInteger create(PoetryUserStudySettingApplicationCommand command) {
        var poetryUserStudySettingCommand = poetryUserStudySettingApplicationMapper.convert(command);
        return poetryUserStudySettingClient.create(poetryUserStudySettingCommand);
    }

    @Override
    public void update(BigInteger id, PoetryUserStudySettingApplicationCommand command) {
        var poetryUserStudySettingCommand = poetryUserStudySettingApplicationMapper.convert(command);
        poetryUserStudySettingClient.update(id, poetryUserStudySettingCommand);
    }
}