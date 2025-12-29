package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.CodeFileSpecMapper;
import com.old.silence.content.api.dto.CodeFileSpecCommand;
import com.old.silence.content.api.dto.CodeFileSpecQuery;
import com.old.silence.content.domain.model.codegen.CodeFileSpec;
import com.old.silence.content.domain.repository.CodeFileSpecRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * CodeFileSpec资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class CodeFileSpecResource implements CodeFileSpecService {
    private final CodeFileSpecRepository codeFileSpecRepository;
    private final CodeFileSpecMapper codeFileSpecMapper;

    public CodeFileSpecResource(CodeFileSpecRepository codeFileSpecRepository,
                                CodeFileSpecMapper codeFileSpecMapper) {
        this.codeFileSpecRepository = codeFileSpecRepository;
        this.codeFileSpecMapper = codeFileSpecMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return codeFileSpecRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(CodeFileSpecQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, CodeFileSpec.class);
        return codeFileSpecRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(CodeFileSpecCommand command) {
        var codeFileSpec = codeFileSpecMapper.convert(command);
        codeFileSpecRepository.create(codeFileSpec);
        return codeFileSpec.getId();
    }

    @Override
    public void update(BigInteger id, CodeFileSpecCommand command) {
        var codeFileSpec = codeFileSpecMapper.convert(command);
        codeFileSpec.setId(id);
        validateModifyingResult(codeFileSpecRepository.update(codeFileSpec));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(codeFileSpecRepository.deleteById(id));
    }
}