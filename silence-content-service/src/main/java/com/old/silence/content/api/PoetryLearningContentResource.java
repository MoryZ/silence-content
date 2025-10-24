package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.assembler.PoetryLearningContentMapper;
import com.old.silence.content.api.dto.PoetryLearningContentCommand;
import com.old.silence.content.api.dto.PoetryLearningContentQuery;
import com.old.silence.content.domain.model.PoetryLearningContent;
import com.old.silence.content.domain.repository.PoetryLearningContentRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
* PoetryLearningContent资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PoetryLearningContentResource implements PoetryLearningContentService {
        private final PoetryLearningContentRepository poetryLearningContentRepository;
        private final PoetryLearningContentMapper poetryLearningContentMapper;

        public PoetryLearningContentResource(PoetryLearningContentRepository poetryLearningContentRepository,
            PoetryLearningContentMapper poetryLearningContentMapper) {
            this.poetryLearningContentRepository = poetryLearningContentRepository;
            this.poetryLearningContentMapper = poetryLearningContentMapper;
        }

        @Override
        public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
            return poetryLearningContentRepository.findById(id, projectionType);
        }

        @Override
        public <T> Page<T> query(PoetryLearningContentQuery query, Pageable pageable, Class<T> projectionType) {
            var criteria = QueryCriteriaConverter.convert(query, PoetryLearningContent.class);
            return poetryLearningContentRepository.findByCriteria(criteria, pageable, projectionType);
        }

        @Override
        public BigInteger create(PoetryLearningContentCommand command) {
            var poetryLearningContent = poetryLearningContentMapper.convert(command);
            poetryLearningContentRepository.create(poetryLearningContent);
            return poetryLearningContent.getId();
        }

        @Override
        public void update(BigInteger id, PoetryLearningContentCommand command) {
            var poetryLearningContent = poetryLearningContentMapper.convert(command);
            poetryLearningContent.setId(id);
            validateModifyingResult(poetryLearningContentRepository.update(poetryLearningContent));
        }

        @Override
        public void deleteById(BigInteger id) {
            validateModifyingResult(poetryLearningContentRepository.deleteById(id));
        }
}