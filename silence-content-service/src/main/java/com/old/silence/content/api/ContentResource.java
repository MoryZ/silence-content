package com.old.silence.content.api;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

import java.math.BigInteger;
import java.util.Optional;

import com.old.silence.content.api.assembler.ContentMapper;
import com.old.silence.content.api.dto.ContentCommand;
import com.old.silence.content.api.dto.ContentQuery;
import com.old.silence.content.domain.model.Content;
import com.old.silence.content.domain.repository.ContentRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;
import com.old.silence.webmvc.util.RestControllerUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.ContentMapper;
import com.old.silence.content.api.dto.ContentCommand;
import com.old.silence.content.api.dto.ContentQuery;
import com.old.silence.content.domain.model.Content;
import com.old.silence.content.domain.repository.ContentRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * @author ruoyi
 *
 * @author murrayZhang
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class ContentResource implements ContentService {

    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;

    public ContentResource(ContentRepository contentRepository,
                           ContentMapper contentMapper) {
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
    }


    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return contentRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(ContentQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, Content.class);
        return contentRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(ContentCommand command) {
        var content = contentMapper.convert(command);
        contentRepository.create(content);
        return content.getId(); // NOSONAR
    }

    @Override
    public void update(BigInteger id, ContentCommand command) {
        var content = contentMapper.convert(command);
        content.setId(id); // NOSONAR
        validateModifyingResult(contentRepository.update(content));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(contentRepository.deleteById(id));
    }
}
