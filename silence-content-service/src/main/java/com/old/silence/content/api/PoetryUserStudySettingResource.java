package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.PoetryUserStudySettingMapper;
import com.old.silence.content.api.dto.PoetryUserStudySettingCommand;
import com.old.silence.content.api.dto.PoetryUserStudySettingQuery;
import com.old.silence.content.domain.model.poetry.PoetryUserStudySetting;
import com.old.silence.content.domain.repository.poetry.PoetryUserStudySettingRepository;
import com.old.silence.content.domain.service.PoetryUserStudySettingDomainService;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * PoetryUserStudySetting资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserStudySettingResource implements PoetryUserStudySettingService {
    private final PoetryUserStudySettingDomainService poetryUserStudySettingDomainService;
    private final PoetryUserStudySettingRepository poetryUserStudySettingRepository;
    private final PoetryUserStudySettingMapper poetryUserStudySettingMapper;

    public PoetryUserStudySettingResource(PoetryUserStudySettingDomainService poetryUserStudySettingService,
                                          PoetryUserStudySettingRepository poetryUserStudySettingRepository,
                                          PoetryUserStudySettingMapper poetryUserStudySettingMapper) {
        this.poetryUserStudySettingDomainService = poetryUserStudySettingService;
        this.poetryUserStudySettingRepository = poetryUserStudySettingRepository;
        this.poetryUserStudySettingMapper = poetryUserStudySettingMapper;
    }

    @Override
    public <T> Optional<T> findBySubCategoryIdGradeIdAndUserId(BigInteger subCategoryId, BigInteger gradeId, BigInteger userId, Class<T> projectionType) {
        return poetryUserStudySettingRepository.findBySubCategoryIdGradeIdAndUserId(subCategoryId, gradeId, userId, projectionType);
    }

    @Override
    public <T> Page<T> query(PoetryUserStudySettingQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, PoetryUserStudySetting.class);
        return poetryUserStudySettingRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public void generateTodayPlan(BigInteger userId, BigInteger subCategoryId) {
        poetryUserStudySettingDomainService.generateTodayPlan(userId, subCategoryId);
    }

    @Override
    public BigInteger create(PoetryUserStudySettingCommand command) {
        var poetryUserStudySetting = poetryUserStudySettingMapper.convert(command);
        poetryUserStudySettingDomainService.create(poetryUserStudySetting);
        return poetryUserStudySetting.getId();
    }

    @Override
    public void update(BigInteger id, PoetryUserStudySettingCommand command) {
        var poetryUserStudySetting = poetryUserStudySettingMapper.convert(command);
        poetryUserStudySetting.setId(id);
        validateModifyingResult(poetryUserStudySettingDomainService.update(poetryUserStudySetting));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(poetryUserStudySettingRepository.deleteById(id));
    }
}