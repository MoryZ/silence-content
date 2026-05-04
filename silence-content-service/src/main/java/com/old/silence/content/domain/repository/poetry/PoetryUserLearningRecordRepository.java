package com.old.silence.content.domain.repository.poetry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.poetry.PoetryUserLearningRecord;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * PoetryUserLearningRecord仓储接口
 */
public interface PoetryUserLearningRecordRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    <T> Optional<T> findByUserIdAndContentId(BigInteger userId, BigInteger contentId, Class<T> projectionType);

    <T> List<T> findByUserIdAndSubCategoryIdAndNextReviewAtLessThanEqual(BigInteger userId, BigInteger subCategoryId, Instant cutoff, Class<T> projectionType);

    int create(PoetryUserLearningRecord poetryUserLearningRecord);

    int update(PoetryUserLearningRecord poetryUserLearningRecord);

    int deleteById(BigInteger id);
}