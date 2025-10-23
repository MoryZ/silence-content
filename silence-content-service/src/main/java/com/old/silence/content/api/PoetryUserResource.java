package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.assembler.PoetryUserMapper;
import com.old.silence.content.api.dto.PoetryUserCommand;
import com.old.silence.content.api.dto.PoetryUserQuery;
import com.old.silence.content.domain.model.PoetryUser;
import com.old.silence.content.domain.repository.PoetryUserRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
* PoetryUser资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PoetryUserResource implements PoetryUserService {
        private final PoetryUserRepository poetryUserRepository;
        private final PoetryUserMapper poetryUserMapper;

        public PoetryUserResource(PoetryUserRepository poetryUserRepository,
        PoetryUserMapper poetryUserMapper) {
            this.poetryUserRepository = poetryUserRepository;
            this.poetryUserMapper = poetryUserMapper;
        }

        @Override
        public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
            return poetryUserRepository.findById(id, projectionType);
        }

        @Override
        public <T> Page<T> query(PoetryUserQuery query, Pageable pageable, Class<T> projectionType) {
            var criteria = QueryCriteriaConverter.convert(query, PoetryUser.class);
            return poetryUserRepository.findByCriteria(criteria, pageable, projectionType);
        }

        @Override
        public BigInteger create(PoetryUserCommand command) {
            var poetryUser = poetryUserMapper.convert(command);
            poetryUserRepository.create(poetryUser);
            return poetryUser.getId();
        }

        @Override
        public void update(BigInteger id, PoetryUserCommand command) {
            var poetryUser = poetryUserMapper.convert(command);
            poetryUser.setId(id);
            validateModifyingResult(poetryUserRepository.update(poetryUser));
        }

        @Override
        public void deleteById(BigInteger id) {
            validateModifyingResult(poetryUserRepository.deleteById(id));
        }
}