package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.assembler.PoetryCategoryMapper;
import com.old.silence.content.api.dto.PoetryCategoryCommand;
import com.old.silence.content.api.dto.PoetryCategoryQuery;
import com.old.silence.content.domain.model.PoetryCategory;
import com.old.silence.content.domain.repository.PoetryCategoryRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
* PoetryCategory资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PoetryCategoryResource implements PoetryCategoryService {
        private final PoetryCategoryRepository poetryCategoryRepository;
        private final PoetryCategoryMapper poetryCategoryMapper;

        public PoetryCategoryResource(PoetryCategoryRepository poetryCategoryRepository,
        PoetryCategoryMapper poetryCategoryMapper) {
            this.poetryCategoryRepository = poetryCategoryRepository;
            this.poetryCategoryMapper = poetryCategoryMapper;
        }

        @Override
        public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
            return poetryCategoryRepository.findById(id, projectionType);
        }

        @Override
        public <T> Page<T> query(PoetryCategoryQuery query, Pageable pageable, Class<T> projectionType) {
            var criteria = QueryCriteriaConverter.convert(query, PoetryCategory.class);
            return poetryCategoryRepository.findByCriteria(criteria, pageable, projectionType);
        }

        @Override
        public BigInteger create(PoetryCategoryCommand command) {
            var poetryCategory = poetryCategoryMapper.convert(command);
            poetryCategoryRepository.create(poetryCategory);
            return poetryCategory.getId();
        }

        @Override
        public void update(BigInteger id, PoetryCategoryCommand command) {
            var poetryCategory = poetryCategoryMapper.convert(command);
            poetryCategory.setId(id);
            validateModifyingResult(poetryCategoryRepository.update(poetryCategory));
        }

        @Override
        public void deleteById(BigInteger id) {
            validateModifyingResult(poetryCategoryRepository.deleteById(id));
        }
}