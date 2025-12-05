package com.old.silence.content.api;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.CodeGenModuleMapper;
import com.old.silence.content.api.dto.CodeGenModuleCommand;
import com.old.silence.content.api.dto.CodeGenModuleQuery;
import com.old.silence.content.domain.model.codegen.CodeGenModule;
import com.old.silence.content.domain.repository.CodeGenModuleRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;
import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * @author moryzang
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class CodeGenModuleResource implements CodeGenModuleService {

    private final CodeGenModuleRepository codeGenModuleRepository;
    private final CodeGenModuleMapper codeGenModuleMapper;

    public CodeGenModuleResource(CodeGenModuleRepository codeGenModuleRepository,
                                 CodeGenModuleMapper codeGenModuleMapper) {
        this.codeGenModuleRepository = codeGenModuleRepository;
        this.codeGenModuleMapper = codeGenModuleMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return codeGenModuleRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> queryPage(CodeGenModuleQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, CodeGenModule.class);
        return codeGenModuleRepository.findByCriteria(criteria, pageable, projectionType);
    }


    @Override
    public BigInteger create(CodeGenModuleCommand command) {
        var codeGenModule = codeGenModuleMapper.convert(command);
        codeGenModuleRepository.create(codeGenModule);
        return codeGenModule.getId(); // NOSONAR
    }

    @Override
    public void updateEnabledById(BigInteger id, boolean enabled) {
        codeGenModuleRepository.updateEnabledById(enabled, id);
    }

    @Override
    public void update(BigInteger id, CodeGenModuleCommand command) {
        var codeGenModule = codeGenModuleMapper.convert(command);
        codeGenModule.setId(id); // NOSONAR
        validateModifyingResult(codeGenModuleRepository.update(codeGenModule));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(codeGenModuleRepository.deleteById(id));
    }
}
