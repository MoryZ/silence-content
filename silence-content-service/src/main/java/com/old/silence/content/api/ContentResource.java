package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.support.ContentMapperFactory;
import com.old.silence.content.api.dto.ContentCommand;
import com.old.silence.content.api.dto.ContentQuery;
import com.old.silence.content.domain.enums.ContentStatus;
import com.old.silence.content.domain.model.Content;
import com.old.silence.content.domain.repository.ContentRepository;
import com.old.silence.content.domain.repository.support.ContentAccessRepositoryFactory;
import com.old.silence.content.domain.service.view.ContentTypeAndIdVIew;
import com.old.silence.core.exception.ResourceNotFoundException;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;


/**
 *
 * @author moryzang
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class ContentResource implements ContentService {


    private final ContentMapperFactory contentMapperFactory;
    private final ContentAccessRepositoryFactory contentRepositoryFactory;
    private final ContentRepository contentRepository;

    public ContentResource(ContentMapperFactory contentMapperFactory,
                           ContentAccessRepositoryFactory contentRepositoryFactory,
                           ContentRepository contentRepository) {
        this.contentMapperFactory = contentMapperFactory;
        this.contentRepositoryFactory = contentRepositoryFactory;
        this.contentRepository = contentRepository;
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
        var mapper = contentMapperFactory.getMapper(command);
        var contentAccessor = mapper.convert(command);

        var repository = contentRepositoryFactory.getRepository(command.getType());
        repository.create(contentAccessor);
        return contentAccessor.getId(); // NOSONAR
    }

    @Override
    public void update(BigInteger id, ContentCommand command) {
        var mapper = contentMapperFactory.getMapper(command);
        var contentAccessor = mapper.convert(command);

        var repository = contentRepositoryFactory.getRepository(command.getType());
        contentAccessor.setId(id); // NOSONAR
        repository.update(contentAccessor);

    }

    @Override
    public void updateStickyTopStatus(BigInteger id, boolean stickyTopStatus) {
        contentRepository.updateStickyTop(id, stickyTopStatus);
    }

    @Override
    public void updateStatus(BigInteger id, ContentStatus contentStatus) {
        contentRepository.updateStatus(id, contentStatus);
    }

    @Override
    public void deleteById(BigInteger id) {
        var type = contentRepository.findById(id, ContentTypeAndIdVIew.class)
                .map(ContentTypeAndIdVIew::getType).orElseThrow(ResourceNotFoundException::new);
        var repository = contentRepositoryFactory.getRepository(type);
        validateModifyingResult(repository.deleteById(id));
    }
}
