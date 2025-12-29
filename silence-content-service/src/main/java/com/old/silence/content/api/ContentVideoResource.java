package com.old.silence.content.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.domain.repository.ContentVideoRepository;

import java.math.BigInteger;
import java.util.Optional;


/**
 *
 * @author moryzang
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class ContentVideoResource implements ContentVideoService {


    private final ContentVideoRepository contentVideoRepository;

    public ContentVideoResource(ContentVideoRepository contentVideoRepository) {
        this.contentVideoRepository = contentVideoRepository;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return contentVideoRepository.findById(id, projectionType);
    }


}
