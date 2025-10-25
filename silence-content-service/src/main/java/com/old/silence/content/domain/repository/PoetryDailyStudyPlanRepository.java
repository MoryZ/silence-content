package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import com.old.silence.content.domain.model.PoetryDailyStudyPlan;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

/**
* PoetryDailyStudyPlan仓储接口
*/
public interface PoetryDailyStudyPlanRepository {

    <T> Optional<T> findByUserIdAndSubCategoryIdAndPlanDate(BigInteger userId, BigInteger subCategoryId, LocalDate planDate, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(PoetryDailyStudyPlan poetryDailyStudyPlan);

    int update(PoetryDailyStudyPlan poetryDailyStudyPlan);

    int deleteById(BigInteger id);
}