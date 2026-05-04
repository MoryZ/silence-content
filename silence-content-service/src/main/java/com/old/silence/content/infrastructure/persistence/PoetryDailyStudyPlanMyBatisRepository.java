package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.poetry.PoetryDailyStudyPlan;
import com.old.silence.content.domain.repository.poetry.PoetryDailyStudyPlanRepository;
import com.old.silence.content.infrastructure.persistence.dao.poetry.PoetryDailyStudyPlanDao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * PoetryDailyStudyPlan仓储实现
 */
@Repository
public class PoetryDailyStudyPlanMyBatisRepository implements PoetryDailyStudyPlanRepository {
    private final PoetryDailyStudyPlanDao poetryDailyStudyPlanDao;

    public PoetryDailyStudyPlanMyBatisRepository(PoetryDailyStudyPlanDao poetryDailyStudyPlanDao) {
        this.poetryDailyStudyPlanDao = poetryDailyStudyPlanDao;
    }

    @Override
    public boolean existsByUserIdAndSubCategoryIdAndPlanDate(BigInteger userId, BigInteger subCategoryId, LocalDate planDate) {
        return poetryDailyStudyPlanDao.existsByUserIdAndSubCategoryIdAndPlanDate(userId, subCategoryId, planDate);
    }

    @Override
    public <T> Optional<T> findByUserIdAndSubCategoryIdAndPlanDate(BigInteger userId, BigInteger subCategoryId, LocalDate planDate, Class<T> projectionType) {
        return poetryDailyStudyPlanDao.findByUserIdAndSubCategoryIdAndPlanDate(userId, subCategoryId, planDate, projectionType);
    }

    @Override
    public <T> List<T> findByUserIdAndPlanDate(BigInteger userId, LocalDate planDate, Class<T> projectionType) {
        return poetryDailyStudyPlanDao.findByUserIdAndPlanDate(userId, planDate, projectionType);
    }

    @Override
    public <T> List<T> findByUserIdAndSubCategoryIdAndPlanDateLessThan(BigInteger userId, BigInteger subCategoryId, LocalDate planDate, Class<T> projectionType) {
        return poetryDailyStudyPlanDao.findByUserIdAndSubCategoryIdAndPlanDateLessThan(userId, subCategoryId, planDate, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return poetryDailyStudyPlanDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(PoetryDailyStudyPlan poetryDailyStudyPlan) {
        return poetryDailyStudyPlanDao.insert(poetryDailyStudyPlan);
    }

    @Override
    public int bulkCreate(List<PoetryDailyStudyPlan> poetryDailyStudyPlans) {
        return poetryDailyStudyPlanDao.insertAll(poetryDailyStudyPlans);
    }

    @Override
    public int update(PoetryDailyStudyPlan poetryDailyStudyPlan) {
        return poetryDailyStudyPlanDao.update(poetryDailyStudyPlan);
    }

    @Override
    public int updateNewItemIdsAndCompletionRate(String newItemIds, BigDecimal completionRate, BigInteger id) {
        return poetryDailyStudyPlanDao.updateNewItemIdsAndCompletionRate(newItemIds, completionRate, id);
    }


    @Override
    public int updateCompletedNewItemsAndCompletionRate(String completeNewItems, BigDecimal completionRate, BigInteger id) {
        return poetryDailyStudyPlanDao.updateCompletedNewItemsAndCompletionRate(completeNewItems, completionRate, id);
    }

    @Override
    public int deleteById(BigInteger id) {
        return poetryDailyStudyPlanDao.deleteById(id);
    }

    @Override
    public int deleteByUserIdAndSubCategoryAndPlanDateGreaterThanEqual(BigInteger userId, BigInteger subCategoryId, LocalDate adjustDate) {
        return poetryDailyStudyPlanDao.deleteByUserIdAndSubCategoryIdAndPlanDateGreaterThanEqual(userId, subCategoryId, adjustDate);
    }
}