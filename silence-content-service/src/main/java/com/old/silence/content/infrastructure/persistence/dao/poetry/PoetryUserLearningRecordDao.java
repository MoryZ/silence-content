package com.old.silence.content.infrastructure.persistence.dao.poetry;

import com.old.silence.content.domain.model.poetry.PoetryUserLearningRecord;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * PoetryUserLearningRecord数据访问接口
 */
public interface PoetryUserLearningRecordDao extends JdbcRepository<PoetryUserLearningRecord, BigInteger> {

    <T> Optional<T> findByUserIdAndContentId(BigInteger userId, BigInteger contentId, Class<T> projectionType);

    <T> List<T> findByUserIdAndSubCategoryIdAndNextReviewAtLessThanEqual(BigInteger userId, BigInteger subCategoryId, Instant cutoff, Class<T> projectionType);

}