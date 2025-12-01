package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.ContentTagMapper;
import com.old.silence.content.api.dto.ContentTagCommand;
import com.old.silence.content.api.dto.ContentTagQuery;
import com.old.silence.content.api.vo.ContentTagTreeVo;
import com.old.silence.content.domain.enums.ContentTagType;
import com.old.silence.content.domain.model.ContentTag;
import com.old.silence.content.domain.repository.ContentTagRepository;
import com.old.silence.content.domain.service.ContentTagDomainService;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.List;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 *
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class ContentTagResource implements ContentTagService {
    private final ContentTagRepository contentTagRepository;
    private final ContentTagMapper contentTagMapper;
    private final ContentTagDomainService contentTagDomainService;


    public ContentTagResource(ContentTagRepository contentTagRepository,
                              ContentTagMapper contentTagMapper,
                              ContentTagDomainService contentTagDomainService) {
        this.contentTagRepository = contentTagRepository;
        this.contentTagMapper = contentTagMapper;
        this.contentTagDomainService = contentTagDomainService;
    }

    @Override
    public List<ContentTagTreeVo> findByParentId(BigInteger id, ContentTagType type, Boolean enabled) {
        return contentTagDomainService.findTags(id, type, enabled);
    }

    @Override
    public <T> List<T> findByTypeAndEnabled(ContentTagType type, Boolean enabled, Class<T> projectionType) {
        return contentTagRepository.findByTypeAndEnabled(type, enabled, projectionType);
    }

    @Override
    public <T> Page<T> query(ContentTagQuery contentTagQuery, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(contentTagQuery, ContentTag.class);
        return contentTagRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(ContentTagCommand command) {
        var content = contentTagMapper.convert(command);
        contentTagRepository.create(content);
        return content.getId(); // NOSONAR
    }

    @Override
    public void update(BigInteger id, ContentTagCommand command) {
        var content = contentTagMapper.convert(command);
        content.setId(id); // NOSONAR
        validateModifyingResult(contentTagRepository.update(content));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(contentTagRepository.deleteById(id));
    }
}
