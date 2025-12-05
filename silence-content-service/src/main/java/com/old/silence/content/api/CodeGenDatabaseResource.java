package com.old.silence.content.api;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.CodeGenDatabaseMapper;
import com.old.silence.content.api.dto.CodeGenDatabaseCommand;
import com.old.silence.content.api.dto.CodeGenDatabaseQuery;
import com.old.silence.content.domain.model.codegen.CodeGenDatabase;
import com.old.silence.content.domain.repository.CodeGenDatabaseRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;
import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * @author moryzang
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class CodeGenDatabaseResource implements CodeGenDatabaseService {

    private final CodeGenDatabaseRepository contentRepository;
    private final CodeGenDatabaseMapper codeGenDatabaseMapper;

    public CodeGenDatabaseResource(CodeGenDatabaseRepository contentRepository,
                                   CodeGenDatabaseMapper codeGenDatabaseMapper) {
        this.contentRepository = contentRepository;
        this.codeGenDatabaseMapper = codeGenDatabaseMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return contentRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> queryPage(CodeGenDatabaseQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, CodeGenDatabase.class);
        return contentRepository.findByCriteria(criteria, pageable, projectionType);
    }


    @Override
    public BigInteger create(CodeGenDatabaseCommand command) {
        var CodeGenDatabase = codeGenDatabaseMapper.convert(command);
        contentRepository.create(CodeGenDatabase);
        return CodeGenDatabase.getId(); // NOSONAR
    }

    @Override
    public void update(BigInteger id, CodeGenDatabaseCommand command) {
        var CodeGenDatabase = codeGenDatabaseMapper.convert(command);
        CodeGenDatabase.setId(id); // NOSONAR
        validateModifyingResult(contentRepository.update(CodeGenDatabase));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(contentRepository.deleteById(id));
    }
}
