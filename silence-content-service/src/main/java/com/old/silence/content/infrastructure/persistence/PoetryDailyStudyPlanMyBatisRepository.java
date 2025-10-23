package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.model.PoetryDailyStudyPlan;
import com.old.silence.content.domain.repository.PoetryDailyStudyPlanRepository;
import com.old.silence.content.infrastructure.persistence.dao.PoetryDailyStudyPlanDao;

import java.math.BigInteger;
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
        public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
            return poetryDailyStudyPlanDao.findById(id, projectionType);
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
        public int update(PoetryDailyStudyPlan poetryDailyStudyPlan) {
            return poetryDailyStudyPlanDao.update(poetryDailyStudyPlan);
        }

        @Override
        public int deleteById(BigInteger id) {
            return poetryDailyStudyPlanDao.deleteById(id);
        }
}