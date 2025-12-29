package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.CodeApiDocumentMapper;
import com.old.silence.content.api.dto.CodeApiDocumentCommand;
import com.old.silence.content.api.dto.CodeApiDocumentQuery;
import com.old.silence.content.domain.model.CodeApiDocument;
import com.old.silence.content.domain.repository.CodeApiDocumentRepository;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * CodeApiDocument资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class CodeApiDocumentResource implements CodeApiDocumentService {
    private final CodeApiDocumentRepository codeApiDocumentRepository;
    private final CodeApiDocumentMapper codeApiDocumentMapper;

    public CodeApiDocumentResource(CodeApiDocumentRepository codeApiDocumentRepository,
                                   CodeApiDocumentMapper codeApiDocumentMapper) {
        this.codeApiDocumentRepository = codeApiDocumentRepository;
        this.codeApiDocumentMapper = codeApiDocumentMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return codeApiDocumentRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(CodeApiDocumentQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, CodeApiDocument.class);
        return codeApiDocumentRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(CodeApiDocumentCommand command) {
        var codeApiDocument = codeApiDocumentMapper.convert(command);
        codeApiDocumentRepository.create(codeApiDocument);
        return codeApiDocument.getId();
    }

    @Override
    public void bulkCreate(List<CodeApiDocumentCommand> codeApiDocumentCommands) {
        var codeApiDocuments = CollectionUtils.transformToList(codeApiDocumentCommands, codeApiDocumentMapper::convert);
        codeApiDocumentRepository.bulkCreate(codeApiDocuments);
    }

    @Override
    public void bulkReplace(List<CodeApiDocumentCommand> codeApiDocumentCommands) {
        var codeApiDocument = CollectionUtils.transformToList(codeApiDocumentCommands, codeApiDocumentMapper::convert);
        var tableName = CollectionUtils.firstElement(codeApiDocument).map(CodeApiDocument::getTableName).orElseThrow(ResourceNotFoundException::new);
        codeApiDocumentRepository.bulkReplace(tableName, codeApiDocument);
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(codeApiDocumentRepository.deleteById(id));
    }
}