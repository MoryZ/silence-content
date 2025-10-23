package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.model.PoetryUserLearningRecord;
import com.old.silence.content.domain.repository.PoetryUserLearningRecordRepository;
import com.old.silence.content.infrastructure.persistence.dao.PoetryUserLearningRecordDao;

import java.math.BigInteger;
import java.util.Optional;

/**
* PoetryUserLearningRecord仓储实现
*/
@Repository
public class PoetryUserLearningRecordMyBatisRepository implements PoetryUserLearningRecordRepository {
        private final PoetryUserLearningRecordDao poetryUserLearningRecordDao;

        public PoetryUserLearningRecordMyBatisRepository(PoetryUserLearningRecordDao poetryUserLearningRecordDao) {
            this.poetryUserLearningRecordDao = poetryUserLearningRecordDao;
        }

        @Override
        public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
            return poetryUserLearningRecordDao.findById(id, projectionType);
        }

        @Override
        public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
            return poetryUserLearningRecordDao.findByCriteria(criteria, pageable, projectionType);
        }

        @Override
        public int create(PoetryUserLearningRecord poetryUserLearningRecord) {
            return poetryUserLearningRecordDao.insert(poetryUserLearningRecord);
        }

        @Override
        public int update(PoetryUserLearningRecord poetryUserLearningRecord) {
            return poetryUserLearningRecordDao.update(poetryUserLearningRecord);
        }

        @Override
        public int deleteById(BigInteger id) {
            return poetryUserLearningRecordDao.deleteById(id);
        }
}