package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.FreemarkerTemplatesMapper;
import com.old.silence.content.api.dto.CodeFileTemplateCommand;
import com.old.silence.content.api.dto.CodeFileTemplateQuery;
import com.old.silence.content.domain.model.codegen.CodeFileTemplate;
import com.old.silence.content.domain.repository.CodeFileTemplateRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;


/**
 * CodeFileTemplate资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class CodeFileTemplateResource implements CodeFileTemplateService {
    private final CodeFileTemplateRepository codeFileTemplateRepository;
    private final FreemarkerTemplatesMapper freemarkerTemplatesMapper;

    public CodeFileTemplateResource(CodeFileTemplateRepository codeFileTemplateRepository,
                                    FreemarkerTemplatesMapper freemarkerTemplatesMapper) {
        this.codeFileTemplateRepository = codeFileTemplateRepository;
        this.freemarkerTemplatesMapper = freemarkerTemplatesMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return codeFileTemplateRepository.findById(id, projectionType);
    }

    @Override
    public <T> Optional<T> findByTemplateName(String templateName, Class<T> projectionType) {
        return codeFileTemplateRepository.findByTemplateName(templateName, projectionType);
    }

    @Override
    public <T> Page<T> query(CodeFileTemplateQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, CodeFileTemplate.class);
        return codeFileTemplateRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(CodeFileTemplateCommand command) {
        var freemarkerTemplates = freemarkerTemplatesMapper.convert(command);
        codeFileTemplateRepository.create(freemarkerTemplates);
        return freemarkerTemplates.getId();
    }

    @Override
    public void update(BigInteger id, CodeFileTemplateCommand command) {
        var freemarkerTemplates = freemarkerTemplatesMapper.convert(command);
        freemarkerTemplates.setId(id);
        validateModifyingResult(codeFileTemplateRepository.update(freemarkerTemplates));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(codeFileTemplateRepository.deleteById(id));
    }
}