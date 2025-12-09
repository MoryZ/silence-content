package com.old.silence.content.api;

import java.lang.Override;
import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.FreemarkerTemplatesMapper;
import com.old.silence.content.api.dto.FreemarkerTemplatesCommand;
import com.old.silence.content.api.dto.FreemarkerTemplatesQuery;
import com.old.silence.content.domain.model.codegen.FreemarkerTemplates;
import com.old.silence.content.domain.repository.FreemarkerTemplatesRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;
import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;


/**
 * FreemarkerTemplates资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class FreemarkerTemplatesResource implements FreemarkerTemplatesService {
    private final FreemarkerTemplatesRepository freemarkerTemplatesRepository;
    private final FreemarkerTemplatesMapper freemarkerTemplatesMapper;

    public FreemarkerTemplatesResource(FreemarkerTemplatesRepository freemarkerTemplatesRepository,
                                       FreemarkerTemplatesMapper freemarkerTemplatesMapper) {
        this.freemarkerTemplatesRepository = freemarkerTemplatesRepository;
        this.freemarkerTemplatesMapper = freemarkerTemplatesMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return freemarkerTemplatesRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(FreemarkerTemplatesQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, FreemarkerTemplates.class);
        return freemarkerTemplatesRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(FreemarkerTemplatesCommand command) {
        var freemarkerTemplates = freemarkerTemplatesMapper.convert(command);
        freemarkerTemplatesRepository.create(freemarkerTemplates);
        return freemarkerTemplates.getId();
    }

    @Override
    public void update(BigInteger id, FreemarkerTemplatesCommand command) {
        var freemarkerTemplates = freemarkerTemplatesMapper.convert(command);
        freemarkerTemplates.setId(id);
        validateModifyingResult(freemarkerTemplatesRepository.update(freemarkerTemplates));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(freemarkerTemplatesRepository.deleteById(id));
    }
}