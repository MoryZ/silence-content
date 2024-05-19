package com.old.silence.content.api;

import com.old.silence.content.api.dto.ContentCommand;
import com.old.silence.content.api.dto.ContentQuery;
import com.old.silence.content.domain.model.Content;
import com.old.silence.content.domain.repository.ContentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Optional;

/**
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/v1")
public class ContentResource implements ContentService {
    private final ContentRepository contentRepository;

    public ContentResource(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }


    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return contentRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(ContentQuery query, Pageable pageable, Class<T> projectionType) {
        QueryCriteriaConverter.convert(query, Content.class).var;
        return null;
    }

    @Override
    public BigInteger create(ContentCommand command) {
        return null;
    }

    @Override
    public void update(BigInteger id, ContentCommand command) {

    }

    @Override
    public void deleteById(BigInteger id) {

    }
}
