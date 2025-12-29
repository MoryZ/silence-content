package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.CodeTableMetaMapper;
import com.old.silence.content.api.dto.CodeApiDocumentCommand;
import com.old.silence.content.api.dto.CodeTableMetaCommand;
import com.old.silence.content.api.dto.CodeTableMetaQuery;
import com.old.silence.content.domain.model.CodeApiDocument;
import com.old.silence.content.domain.model.CodeTableMeta;
import com.old.silence.content.domain.repository.CodeTableMetaRepository;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * CodeTableMeta资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class CodeTableMetaResource implements CodeTableMetaService {
    private final CodeTableMetaRepository codeTableMetaRepository;
    private final CodeTableMetaMapper codeTableMetaMapper;

    public CodeTableMetaResource(CodeTableMetaRepository codeTableMetaRepository,
                                 CodeTableMetaMapper codeTableMetaMapper) {
        this.codeTableMetaRepository = codeTableMetaRepository;
        this.codeTableMetaMapper = codeTableMetaMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return codeTableMetaRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(CodeTableMetaQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, CodeTableMeta.class);
        return codeTableMetaRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(CodeTableMetaCommand command) {
        var codeTableMeta = codeTableMetaMapper.convert(command);
        codeTableMetaRepository.create(codeTableMeta);
        return codeTableMeta.getId();
    }

    @Override
    public void bulkCreate(List<CodeTableMetaCommand> codeTableMetaCommands) {
        var codeTableMetas = CollectionUtils.transformToList(codeTableMetaCommands, codeTableMetaMapper::convert);
        codeTableMetaRepository.bulkCreate(codeTableMetas);
    }

    @Override
    public void bulkReplace(List<CodeTableMetaCommand> codeTableMetaCommands) {
        var codeTableMetas = CollectionUtils.transformToList(codeTableMetaCommands, codeTableMetaMapper::convert);
        var codeTableMeta = CollectionUtils.firstElement(codeTableMetas)
                .orElseThrow(ResourceNotFoundException::new);
        codeTableMetaRepository.bulkReplace(codeTableMeta.getSchemaName(), codeTableMeta.getTableName(), codeTableMetas);
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(codeTableMetaRepository.deleteById(id));
    }
}