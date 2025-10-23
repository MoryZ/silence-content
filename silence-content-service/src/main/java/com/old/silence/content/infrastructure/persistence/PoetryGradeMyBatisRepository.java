package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.model.PoetryGrade;
import com.old.silence.content.domain.repository.PoetryGradeRepository;
import com.old.silence.content.infrastructure.persistence.dao.PoetryGradeDao;

import java.math.BigInteger;
import java.util.Optional;

/**
* PoetryGrade仓储实现
*/
@Repository
public class PoetryGradeMyBatisRepository implements PoetryGradeRepository {
        private final PoetryGradeDao poetryGradeDao;

        public PoetryGradeMyBatisRepository(PoetryGradeDao poetryGradeDao) {
            this.poetryGradeDao = poetryGradeDao;
        }

        @Override
        public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
            return poetryGradeDao.findById(id, projectionType);
        }

        @Override
        public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
            return poetryGradeDao.findByCriteria(criteria, pageable, projectionType);
        }

        @Override
        public int create(PoetryGrade poetryGrade) {
            return poetryGradeDao.insert(poetryGrade);
        }

        @Override
        public int update(PoetryGrade poetryGrade) {
            return poetryGradeDao.update(poetryGrade);
        }

        @Override
        public int deleteById(BigInteger id) {
            return poetryGradeDao.deleteById(id);
        }
}