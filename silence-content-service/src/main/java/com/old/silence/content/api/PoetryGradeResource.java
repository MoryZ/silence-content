package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.assembler.PoetryGradeMapper;
import com.old.silence.content.api.dto.PoetryGradeCommand;
import com.old.silence.content.api.dto.PoetryGradeQuery;
import com.old.silence.content.domain.model.PoetryGrade;
import com.old.silence.content.domain.repository.PoetryGradeRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
* PoetryGrade资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PoetryGradeResource implements PoetryGradeService {
        private final PoetryGradeRepository poetryGradeRepository;
        private final PoetryGradeMapper poetryGradeMapper;

        public PoetryGradeResource(PoetryGradeRepository poetryGradeRepository,
            PoetryGradeMapper poetryGradeMapper) {
            this.poetryGradeRepository = poetryGradeRepository;
            this.poetryGradeMapper = poetryGradeMapper;
        }

        @Override
        public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
            return poetryGradeRepository.findById(id, projectionType);
        }

        @Override
        public <T> Page<T> query(PoetryGradeQuery query, Pageable pageable, Class<T> projectionType) {
            var criteria = QueryCriteriaConverter.convert(query, PoetryGrade.class);
            return poetryGradeRepository.findByCriteria(criteria, pageable, projectionType);
        }

        @Override
        public BigInteger create(PoetryGradeCommand command) {
            var poetryGrade = poetryGradeMapper.convert(command);
            poetryGradeRepository.create(poetryGrade);
            return poetryGrade.getId();
        }

        @Override
        public void update(BigInteger id, PoetryGradeCommand command) {
            var poetryGrade = poetryGradeMapper.convert(command);
            poetryGrade.setId(id);
            validateModifyingResult(poetryGradeRepository.update(poetryGrade));
        }

        @Override
        public void deleteById(BigInteger id) {
            validateModifyingResult(poetryGradeRepository.deleteById(id));
        }
}