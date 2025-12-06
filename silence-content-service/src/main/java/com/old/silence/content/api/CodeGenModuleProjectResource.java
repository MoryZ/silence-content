package com.old.silence.content.api;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.CodeGenProjectModuleMapper;
import com.old.silence.content.api.dto.CodeGenProjectModuleCommand;
import com.old.silence.content.api.dto.CodeGenProjectModuleQuery;
import com.old.silence.content.domain.model.codegen.CodeGenProjectModule;
import com.old.silence.content.domain.repository.CodeGenProjectModuleRepository;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

/**
 * @author moryzang
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class CodeGenModuleProjectResource implements CodeGenProjectModuleService {

    private final CodeGenProjectModuleRepository codeGenProjectModuleRepository;
    private final CodeGenProjectModuleMapper codeGenProjectModuleMapper;

    public CodeGenModuleProjectResource(CodeGenProjectModuleRepository codeGenProjectModuleRepository,
                                        CodeGenProjectModuleMapper codeGenProjectModuleMapper) {
        this.codeGenProjectModuleRepository = codeGenProjectModuleRepository;
        this.codeGenProjectModuleMapper = codeGenProjectModuleMapper;
    }

    @Override
    public <T> List<T> findByProjectId(BigInteger projectId, Class<T> projectionType) {
        return codeGenProjectModuleRepository.findByProjectId(projectId, projectionType);
    }

    @Override
    public <T> Page<T> queryPage(CodeGenProjectModuleQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, CodeGenProjectModule.class);
        return codeGenProjectModuleRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int bulkReplace(List<CodeGenProjectModuleCommand> commands) {
        var codeGenProjectModules = CollectionUtils.transformToList(commands, codeGenProjectModuleMapper::convert);
        return codeGenProjectModuleRepository.bulkReplace(codeGenProjectModules);
    }
}
