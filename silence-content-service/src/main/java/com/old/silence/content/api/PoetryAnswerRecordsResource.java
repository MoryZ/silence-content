package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.assembler.PoetryAnswerRecordsMapper;
import com.old.silence.content.api.dto.PoetryAnswerRecordsCommand;
import com.old.silence.content.api.dto.PoetryAnswerRecordsQuery;
import com.old.silence.content.domain.model.PoetryAnswerRecords;
import com.old.silence.content.domain.repository.PoetryAnswerRecordsRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
* PoetryAnswerRecords资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PoetryAnswerRecordsResource implements PoetryAnswerRecordsService {
    private final PoetryAnswerRecordsRepository poetryAnswerRecordsRepository;
    private final PoetryAnswerRecordsMapper poetryAnswerRecordsMapper;

    public PoetryAnswerRecordsResource(PoetryAnswerRecordsRepository poetryAnswerRecordsRepository,
                                PoetryAnswerRecordsMapper poetryAnswerRecordsMapper) {
        this.poetryAnswerRecordsRepository = poetryAnswerRecordsRepository;
        this.poetryAnswerRecordsMapper = poetryAnswerRecordsMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryAnswerRecordsRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(PoetryAnswerRecordsQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, PoetryAnswerRecords.class);
        return poetryAnswerRecordsRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(PoetryAnswerRecordsCommand command) {
        var poetryAnswerRecords = poetryAnswerRecordsMapper.convert(command);
        poetryAnswerRecordsRepository.create(poetryAnswerRecords);
        return poetryAnswerRecords.getId();
    }

    @Override
    public void update(BigInteger id, PoetryAnswerRecordsCommand command) {
        var poetryAnswerRecords = poetryAnswerRecordsMapper.convert(command);
        poetryAnswerRecords.setId(id);
        validateModifyingResult(poetryAnswerRecordsRepository.update(poetryAnswerRecords));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(poetryAnswerRecordsRepository.deleteById(id));
    }
}