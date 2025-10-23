package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.assembler.PoetryUserFavoriteMapper;
import com.old.silence.content.api.dto.PoetryUserFavoriteCommand;
import com.old.silence.content.api.dto.PoetryUserFavoriteQuery;
import com.old.silence.content.domain.model.PoetryUserFavorite;
import com.old.silence.content.domain.repository.PoetryUserFavoriteRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
* PoetryUserFavorite资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PoetryUserFavoriteResource implements PoetryUserFavoriteService {
        private final PoetryUserFavoriteRepository poetryUserFavoriteRepository;
        private final PoetryUserFavoriteMapper poetryUserFavoriteMapper;

        public PoetryUserFavoriteResource(PoetryUserFavoriteRepository poetryUserFavoriteRepository,
        PoetryUserFavoriteMapper poetryUserFavoriteMapper) {
            this.poetryUserFavoriteRepository = poetryUserFavoriteRepository;
            this.poetryUserFavoriteMapper = poetryUserFavoriteMapper;
        }

        @Override
        public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
            return poetryUserFavoriteRepository.findById(id, projectionType);
        }

        @Override
        public <T> Page<T> query(PoetryUserFavoriteQuery query, Pageable pageable, Class<T> projectionType) {
            var criteria = QueryCriteriaConverter.convert(query, PoetryUserFavorite.class);
            return poetryUserFavoriteRepository.findByCriteria(criteria, pageable, projectionType);
        }

        @Override
        public BigInteger create(PoetryUserFavoriteCommand command) {
            var poetryUserFavorite = poetryUserFavoriteMapper.convert(command);
            poetryUserFavoriteRepository.create(poetryUserFavorite);
            return poetryUserFavorite.getId();
        }

        @Override
        public void update(BigInteger id, PoetryUserFavoriteCommand command) {
            var poetryUserFavorite = poetryUserFavoriteMapper.convert(command);
            poetryUserFavorite.setId(id);
            validateModifyingResult(poetryUserFavoriteRepository.update(poetryUserFavorite));
        }

        @Override
        public void deleteById(BigInteger id) {
            validateModifyingResult(poetryUserFavoriteRepository.deleteById(id));
        }
}