package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.assembler.PromptTemplateMapper;
import com.old.silence.content.api.dto.PromptTemplateCommand;
import com.old.silence.content.api.dto.PromptTemplateQuery;
import com.old.silence.content.domain.enums.PromptTemplateType;
import com.old.silence.content.domain.model.poetry.PromptTemplate;
import com.old.silence.content.domain.repository.poetry.PromptTemplateRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
* PromptTemplate资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PromptTemplateResource implements PromptTemplateService {
    private final PromptTemplateRepository promptTemplateRepository;
    private final PromptTemplateMapper promptTemplateMapper;

    public PromptTemplateResource(PromptTemplateRepository promptTemplateRepository,
                                PromptTemplateMapper promptTemplateMapper) {
        this.promptTemplateRepository = promptTemplateRepository;
        this.promptTemplateMapper = promptTemplateMapper;
    }

    @Override
    public <T> Optional<T> findBySubCategoryIdAndTemplateType(BigInteger subCategoryId, PromptTemplateType templateType, Class<T> projectionType) {
        return promptTemplateRepository.findBySubCategoryIdAndTemplateType(subCategoryId, templateType, projectionType);
    }

    @Override
    public <T> Page<T> query(PromptTemplateQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, PromptTemplate.class);
        return promptTemplateRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(PromptTemplateCommand command) {
        var promptTemplate = promptTemplateMapper.convert(command);
        promptTemplateRepository.create(promptTemplate);
                        return promptTemplate.getId();
                        }

    @Override
    public void update(BigInteger id, PromptTemplateCommand command) {
        var promptTemplate = promptTemplateMapper.convert(command);
        promptTemplate.setId(id);
        validateModifyingResult(promptTemplateRepository.update(promptTemplate));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(promptTemplateRepository.deleteById(id));
    }
}