package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.PoetryDailyStudyPlan;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
* PoetryDailyStudyPlan数据访问接口
*/
public interface PoetryDailyStudyPlanDao extends JdbcRepository<PoetryDailyStudyPlan, BigInteger> {

    <T> Optional<T> findByUserIdAndSubCategoryIdAndPlanDate(BigInteger userId, BigInteger categoryId, LocalDate planDate, Class<T> projectionType);

    <T> List<T> findByUserIdAndSubCategoryIdAndPlanDateLessThan(BigInteger userId, BigInteger subCategoryId, LocalDate planDate,
                                                                Class<T> projectionType);

    int updateNewItemIdsAndCompletionRate(String newItemIds, BigDecimal completionRate, BigInteger id);

    int updateCompletedNewItemsAndCompletionRate(String newItemIds, BigDecimal completionRate, BigInteger id);

    int deleteByUserIdAndSubCategoryIdAndPlanDateGreaterThanEqual(BigInteger userId, BigInteger subCategoryId, LocalDate planDate);

}