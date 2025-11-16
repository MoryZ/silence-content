package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.assembler.PromptCommonFormatMapper;
import com.old.silence.content.api.dto.PromptCommonFormatCommand;
import com.old.silence.content.api.dto.PromptCommonFormatQuery;
import com.old.silence.content.domain.enums.PromptFormatType;
import com.old.silence.content.domain.model.PromptCommonFormat;
import com.old.silence.content.domain.repository.PromptCommonFormatRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
* PromptCommonFormat资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PromptCommonFormatResource implements PromptCommonFormatService {
    private final PromptCommonFormatRepository promptCommonFormatRepository;
    private final PromptCommonFormatMapper promptCommonFormatMapper;

    public PromptCommonFormatResource(PromptCommonFormatRepository promptCommonFormatRepository,
                                PromptCommonFormatMapper promptCommonFormatMapper) {
        this.promptCommonFormatRepository = promptCommonFormatRepository;
        this.promptCommonFormatMapper = promptCommonFormatMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return promptCommonFormatRepository.findById(id, projectionType);
    }

    @Override
    public <T> Optional<T> findByFormatType(PromptFormatType formatType, Class<T> projectionType) {
        return promptCommonFormatRepository.findByFormatType(formatType, projectionType);
    }

    @Override
    public <T> Page<T> query(PromptCommonFormatQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, PromptCommonFormat.class);
        return promptCommonFormatRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(PromptCommonFormatCommand command) {
        var promptCommonFormat = promptCommonFormatMapper.convert(command);
        promptCommonFormatRepository.create(promptCommonFormat);
                        return promptCommonFormat.getId();
                        }

    @Override
    public void update(BigInteger id, PromptCommonFormatCommand command) {
        var promptCommonFormat = promptCommonFormatMapper.convert(command);
        promptCommonFormat.setId(id);
        validateModifyingResult(promptCommonFormatRepository.update(promptCommonFormat));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(promptCommonFormatRepository.deleteById(id));
    }
}