package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.PoetryUserLoginLogMapper;
import com.old.silence.content.api.dto.PoetryUserLoginLogCommand;
import com.old.silence.content.api.dto.PoetryUserLoginLogQuery;
import com.old.silence.content.domain.model.poetry.PoetryUserLoginLog;
import com.old.silence.content.domain.repository.poetry.PoetryUserLoginLogRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * PoetryUserLoginLog资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserLoginLogResource implements PoetryUserLoginLogService {
    private final PoetryUserLoginLogRepository poetryUserLoginLogRepository;
    private final PoetryUserLoginLogMapper poetryUserLoginLogMapper;

    public PoetryUserLoginLogResource(PoetryUserLoginLogRepository poetryUserLoginLogRepository,
                                      PoetryUserLoginLogMapper poetryUserLoginLogMapper) {
        this.poetryUserLoginLogRepository = poetryUserLoginLogRepository;
        this.poetryUserLoginLogMapper = poetryUserLoginLogMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryUserLoginLogRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(PoetryUserLoginLogQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, PoetryUserLoginLog.class);
        return poetryUserLoginLogRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(PoetryUserLoginLogCommand command) {
        var poetryUserLoginLog = poetryUserLoginLogMapper.convert(command);
        poetryUserLoginLogRepository.create(poetryUserLoginLog);
        return poetryUserLoginLog.getId();
    }

    @Override
    public void update(BigInteger id, PoetryUserLoginLogCommand command) {
        var poetryUserLoginLog = poetryUserLoginLogMapper.convert(command);
        poetryUserLoginLog.setId(id);
        validateModifyingResult(poetryUserLoginLogRepository.update(poetryUserLoginLog));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(poetryUserLoginLogRepository.deleteById(id));
    }
}