package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.PoetryUserLoginLogMapper;
import com.old.silence.content.api.dto.PoetryUserLoginLogCommand;
import com.old.silence.content.api.dto.PoetryUserLoginLogQuery;
import com.old.silence.content.domain.model.PoetryUser;
import com.old.silence.content.domain.repository.PoetryUserLoginLogRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * PoetryUser资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserLoginLogResource implements PoetryUserLoginLogService {
    private final PoetryUserLoginLogRepository poetryUserRepository;
    private final PoetryUserLoginLogMapper poetryUserLoginLogMapper;

    public PoetryUserLoginLogResource(PoetryUserLoginLogRepository poetryUserRepository,
                                      PoetryUserLoginLogMapper poetryUserLoginLogMapper) {
        this.poetryUserRepository = poetryUserRepository;
        this.poetryUserLoginLogMapper = poetryUserLoginLogMapper;
    }


    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryUserRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(PoetryUserLoginLogQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, PoetryUser.class);
        return poetryUserRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(PoetryUserLoginLogCommand command) {
        var poetryUserLoginLog = poetryUserLoginLogMapper.convert(command);
        poetryUserRepository.create(poetryUserLoginLog);
        return poetryUserLoginLog.getId();
    }

    @Override
    public void update(BigInteger id, PoetryUserLoginLogCommand command) {
        var poetryUserLoginLog = poetryUserLoginLogMapper.convert(command);
        poetryUserLoginLog.setId(id);
        validateModifyingResult(poetryUserRepository.update(poetryUserLoginLog));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(poetryUserRepository.deleteById(id));
    }
}