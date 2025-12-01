package com.old.silence.content.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.domain.repository.ContentArticleRepository;

import java.math.BigInteger;
import java.util.Optional;


/**
 *
 * @author moryzang
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class ContentArticleResource implements ContentArticleService {


    private final ContentArticleRepository contentArticleRepository;

    public ContentArticleResource(ContentArticleRepository contentArticleRepository) {
        this.contentArticleRepository = contentArticleRepository;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return contentArticleRepository.findById(id, projectionType);
    }


}
