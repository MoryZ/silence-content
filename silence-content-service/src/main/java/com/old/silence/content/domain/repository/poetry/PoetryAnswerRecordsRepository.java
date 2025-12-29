package com.old.silence.content.domain.repository.poetry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.api.vo.StatsVo;
import com.old.silence.content.domain.model.poetry.PoetryAnswerRecords;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * PoetryAnswerRecords仓储接口
 */
public interface PoetryAnswerRecordsRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> List<T> findByContentIdAndSubCategoryIdAndUserId(BigInteger contentId, BigInteger subCategoryId, BigInteger userId, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    List<StatsVo> findMaxAccuracyTop5();

    List<StatsVo> findMaxAnswerTop5();

    int create(PoetryAnswerRecords poetryAnswerRecords);

    int update(PoetryAnswerRecords poetryAnswerRecords);

    int deleteById(BigInteger id);


}