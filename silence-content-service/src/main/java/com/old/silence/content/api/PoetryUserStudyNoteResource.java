package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.assembler.PoetryUserStudyNoteMapper;
import com.old.silence.content.api.dto.PoetryUserStudyNoteCommand;
import com.old.silence.content.api.dto.PoetryUserStudyNoteQuery;
import com.old.silence.content.domain.model.PoetryUserStudyNote;
import com.old.silence.content.domain.repository.PoetryUserStudyNoteRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
* PoetryUserStudyNote资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PoetryUserStudyNoteResource implements PoetryUserStudyNoteService {
        private final PoetryUserStudyNoteRepository poetryUserStudyNoteRepository;
        private final PoetryUserStudyNoteMapper poetryUserStudyNoteMapper;

        public PoetryUserStudyNoteResource(PoetryUserStudyNoteRepository poetryUserStudyNoteRepository,
            PoetryUserStudyNoteMapper poetryUserStudyNoteMapper) {
            this.poetryUserStudyNoteRepository = poetryUserStudyNoteRepository;
            this.poetryUserStudyNoteMapper = poetryUserStudyNoteMapper;
        }

        @Override
        public <T> List<T> findByContentIdAndUserId(BigInteger contentId, BigInteger userId, Class<T> projectionType) {
            return poetryUserStudyNoteRepository.findByContentIdAndUserId(contentId, userId, projectionType);
        }

        @Override
        public <T> Page<T> query(PoetryUserStudyNoteQuery query, Pageable pageable, Class<T> projectionType) {
            var criteria = QueryCriteriaConverter.convert(query, PoetryUserStudyNote.class);
            return poetryUserStudyNoteRepository.findByCriteria(criteria, pageable, projectionType);
        }

        @Override
        public BigInteger create(PoetryUserStudyNoteCommand command) {
            var poetryUserStudyNote = poetryUserStudyNoteMapper.convert(command);
            poetryUserStudyNoteRepository.create(poetryUserStudyNote);
            return poetryUserStudyNote.getId();
        }

        @Override
        public void update(BigInteger id, PoetryUserStudyNoteCommand command) {
            var poetryUserStudyNote = poetryUserStudyNoteMapper.convert(command);
            poetryUserStudyNote.setId(id);
            validateModifyingResult(poetryUserStudyNoteRepository.update(poetryUserStudyNote));
        }

        @Override
        public void deleteById(BigInteger id) {
            validateModifyingResult(poetryUserStudyNoteRepository.deleteById(id));
        }
}