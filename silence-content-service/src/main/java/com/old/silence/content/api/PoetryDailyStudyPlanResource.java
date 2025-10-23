package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.old.silence.content.api.assembler.PoetryDailyStudyPlanMapper;
import com.old.silence.content.api.dto.PoetryDailyStudyPlanCommand;
import com.old.silence.content.api.dto.PoetryDailyStudyPlanQuery;
import com.old.silence.content.domain.model.PoetryDailyStudyPlan;
import com.old.silence.content.domain.repository.PoetryDailyStudyPlanRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
* PoetryDailyStudyPlan资源控制器
*/
@RestController
@RequestMapping("/api/v1")
public class PoetryDailyStudyPlanResource implements PoetryDailyStudyPlanService {
        private final PoetryDailyStudyPlanRepository poetryDailyStudyPlanRepository;
        private final PoetryDailyStudyPlanMapper poetryDailyStudyPlanMapper;

        public PoetryDailyStudyPlanResource(PoetryDailyStudyPlanRepository poetryDailyStudyPlanRepository,
        PoetryDailyStudyPlanMapper poetryDailyStudyPlanMapper) {
            this.poetryDailyStudyPlanRepository = poetryDailyStudyPlanRepository;
            this.poetryDailyStudyPlanMapper = poetryDailyStudyPlanMapper;
        }

        @Override
        public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
            return poetryDailyStudyPlanRepository.findById(id, projectionType);
        }

        @Override
        public <T> Page<T> query(PoetryDailyStudyPlanQuery query, Pageable pageable, Class<T> projectionType) {
            var criteria = QueryCriteriaConverter.convert(query, PoetryDailyStudyPlan.class);
            return poetryDailyStudyPlanRepository.findByCriteria(criteria, pageable, projectionType);
        }

        @Override
        public BigInteger create(PoetryDailyStudyPlanCommand command) {
            var poetryDailyStudyPlan = poetryDailyStudyPlanMapper.convert(command);
            poetryDailyStudyPlanRepository.create(poetryDailyStudyPlan);
            return poetryDailyStudyPlan.getId();
        }

        @Override
        public void update(BigInteger id, PoetryDailyStudyPlanCommand command) {
            var poetryDailyStudyPlan = poetryDailyStudyPlanMapper.convert(command);
            poetryDailyStudyPlan.setId(id);
            validateModifyingResult(poetryDailyStudyPlanRepository.update(poetryDailyStudyPlan));
        }

        @Override
        public void deleteById(BigInteger id) {
            validateModifyingResult(poetryDailyStudyPlanRepository.deleteById(id));
        }
}