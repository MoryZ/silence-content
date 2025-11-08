package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.PoetryAnswerRecordsMapper;
import com.old.silence.content.api.dto.PoetryAnswerRecordsCommand;
import com.old.silence.content.api.dto.PoetryAnswerRecordsQuery;
import com.old.silence.content.api.vo.StatsVo;
import com.old.silence.content.domain.model.PoetryAnswerRecords;
import com.old.silence.content.domain.repository.PoetryAnswerRecordsRepository;
import com.old.silence.content.infrastructure.persistence.dao.support.NumberStatsVo;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.List;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * PoetryAnswerRecords资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryAnswerRecordsResource implements PoetryAnswerRecordsService {
    private final PoetryAnswerRecordsRepository poetryAnswerRecordsRepository;
    private final PoetryAnswerRecordsMapper poetryAnswerRecordsMapper;

    public PoetryAnswerRecordsResource(PoetryAnswerRecordsRepository poetryAnswerRecordsRepository,
                                       PoetryAnswerRecordsMapper poetryAnswerRecordsMapper) {
        this.poetryAnswerRecordsRepository = poetryAnswerRecordsRepository;
        this.poetryAnswerRecordsMapper = poetryAnswerRecordsMapper;
    }

    @Override
    public <T> List<T> findByContentIdAndSubCategoryIdAndUserId(BigInteger contentId, BigInteger subCategoryId, BigInteger userId, Class<T> projectionType) {
        return poetryAnswerRecordsRepository.findByContentIdAndSubCategoryIdAndUserId(contentId, subCategoryId, userId, projectionType);
    }

    @Override
    public <T> Page<T> query(PoetryAnswerRecordsQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, PoetryAnswerRecords.class);
        return poetryAnswerRecordsRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public List<StatsVo> findMaxAccuracyTop5() {
        return CollectionUtils.transformToList(poetryAnswerRecordsRepository.findMaxAccuracyTop5(),
                bigDecimalStatsVo -> new StatsVo(bigDecimalStatsVo.getUserId(), bigDecimalStatsVo.getAccuracy()));
    }

    @Override
    public List<StatsVo> findMaxAnswerTop5() {
        var maxAnswerTop5 = poetryAnswerRecordsRepository.findMaxAnswerTop5();
        return CollectionUtils.transformToList(maxAnswerTop5, numberStatsVo ->
                new StatsVo(numberStatsVo.getUserId(), numberStatsVo.getIndicatorAccumulation()));
    }

    @Override
    public BigInteger create(PoetryAnswerRecordsCommand command) {
        var poetryAnswerRecords = poetryAnswerRecordsMapper.convert(command);
        poetryAnswerRecordsRepository.create(poetryAnswerRecords);
        return poetryAnswerRecords.getId();
    }

    @Override
    public void update(BigInteger id, PoetryAnswerRecordsCommand command) {
        var poetryAnswerRecords = poetryAnswerRecordsMapper.convert(command);
        poetryAnswerRecords.setId(id);
        validateModifyingResult(poetryAnswerRecordsRepository.update(poetryAnswerRecords));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(poetryAnswerRecordsRepository.deleteById(id));
    }
}