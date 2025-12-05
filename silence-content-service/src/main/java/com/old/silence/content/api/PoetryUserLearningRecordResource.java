package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.PoetryUserLearningRecordMapper;
import com.old.silence.content.api.dto.PoetryUserLearningRecordCommand;
import com.old.silence.content.api.dto.PoetryUserLearningRecordQuery;
import com.old.silence.content.domain.model.poetry.PoetryUserLearningRecord;
import com.old.silence.content.domain.repository.poetry.PoetryUserLearningRecordRepository;
import com.old.silence.content.domain.service.PoetryUserLearningRecordDomainService;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * PoetryUserLearningRecord资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserLearningRecordResource implements PoetryUserLearningRecordService {
    private final PoetryUserLearningRecordDomainService poetryUserLearningRecordDomainService;
    private final PoetryUserLearningRecordRepository poetryUserLearningRecordRepository;
    private final PoetryUserLearningRecordMapper poetryUserLearningRecordMapper;

    public PoetryUserLearningRecordResource(PoetryUserLearningRecordDomainService poetryUserLearningRecordDomainService, PoetryUserLearningRecordRepository poetryUserLearningRecordRepository,
                                            PoetryUserLearningRecordMapper poetryUserLearningRecordMapper) {
        this.poetryUserLearningRecordDomainService = poetryUserLearningRecordDomainService;
        this.poetryUserLearningRecordRepository = poetryUserLearningRecordRepository;
        this.poetryUserLearningRecordMapper = poetryUserLearningRecordMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryUserLearningRecordRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(PoetryUserLearningRecordQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, PoetryUserLearningRecord.class);
        return poetryUserLearningRecordRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(PoetryUserLearningRecordCommand command) {
        var poetryUserLearningRecord = poetryUserLearningRecordMapper.convert(command);
        poetryUserLearningRecordDomainService.create(poetryUserLearningRecord);
        return poetryUserLearningRecord.getId();
    }

    @Override
    public void update(BigInteger id, PoetryUserLearningRecordCommand command) {
        var poetryUserLearningRecord = poetryUserLearningRecordMapper.convert(command);
        poetryUserLearningRecord.setId(id);
        validateModifyingResult(poetryUserLearningRecordRepository.update(poetryUserLearningRecord));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(poetryUserLearningRecordRepository.deleteById(id));
    }
}