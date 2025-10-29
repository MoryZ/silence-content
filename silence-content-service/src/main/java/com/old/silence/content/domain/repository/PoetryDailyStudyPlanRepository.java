package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import com.old.silence.content.domain.model.PoetryDailyStudyPlan;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
* PoetryDailyStudyPlan仓储接口
*/
public interface PoetryDailyStudyPlanRepository {

    <T> Optional<T> findByUserIdAndSubCategoryIdAndPlanDate(BigInteger userId, BigInteger subCategoryId, LocalDate planDate, Class<T> projectionType);

    <T> List<T> findByUserIdAndSubCategoryIdAndPlanDateLessThan(BigInteger userId, BigInteger subCategoryId, LocalDate planDate, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(PoetryDailyStudyPlan poetryDailyStudyPlan);

    int bulkCreate(List<PoetryDailyStudyPlan> poetryDailyStudyPlans);

    int update(PoetryDailyStudyPlan poetryDailyStudyPlan);

    int updateNewItemIdsAndCompletionRate(String newItemIds, BigDecimal completionRate, BigInteger id);

    int updateCompletedNewItemsAndCompletionRate(String completeNewItems, BigDecimal completionRate, BigInteger id);

    int deleteById(BigInteger id);

    int deleteByUserIdAndSubCategoryAndPlanDateGreaterThanEqual(BigInteger userId, BigInteger subCategoryId, LocalDate adjustDate);
}