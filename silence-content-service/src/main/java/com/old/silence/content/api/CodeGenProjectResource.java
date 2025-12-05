package com.old.silence.content.api;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.CodeGenProjectMapper;
import com.old.silence.content.api.dto.CodeGenProjectCommand;
import com.old.silence.content.api.dto.CodeGenProjectQuery;
import com.old.silence.content.domain.model.codegen.CodeGenProject;
import com.old.silence.content.domain.repository.CodeGenProjectRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;
import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * @author moryzang
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class CodeGenProjectResource implements CodeGenProjectService {

    private final CodeGenProjectRepository codeGenProjectRepository;
    private final CodeGenProjectMapper codeGenProjectMapper;

    public CodeGenProjectResource(CodeGenProjectRepository codeGenProjectRepository,
                                  CodeGenProjectMapper codeGenProjectMapper) {
        this.codeGenProjectRepository = codeGenProjectRepository;
        this.codeGenProjectMapper = codeGenProjectMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return codeGenProjectRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> queryPage(CodeGenProjectQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, CodeGenProject.class);
        return codeGenProjectRepository.findByCriteria(criteria, pageable, projectionType);
    }


    @Override
    public BigInteger create(CodeGenProjectCommand command) {
        var CodeGenProject = codeGenProjectMapper.convert(command);
        codeGenProjectRepository.create(CodeGenProject);
        return CodeGenProject.getId(); // NOSONAR
    }

    @Override
    public void update(BigInteger id, CodeGenProjectCommand command) {
        var CodeGenProject = codeGenProjectMapper.convert(command);
        CodeGenProject.setId(id); // NOSONAR
        validateModifyingResult(codeGenProjectRepository.update(CodeGenProject));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(codeGenProjectRepository.deleteById(id));
    }
}
