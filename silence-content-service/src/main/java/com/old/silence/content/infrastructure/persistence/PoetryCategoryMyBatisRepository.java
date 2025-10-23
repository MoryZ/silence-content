package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.model.PoetryCategory;
import com.old.silence.content.domain.repository.PoetryCategoryRepository;
import com.old.silence.content.infrastructure.persistence.dao.PoetryCategoryDao;

import java.math.BigInteger;
import java.util.Optional;

/**
* PoetryCategory仓储实现
*/
@Repository
public class PoetryCategoryMyBatisRepository implements PoetryCategoryRepository {
        private final PoetryCategoryDao poetryCategoryDao;

        public PoetryCategoryMyBatisRepository(PoetryCategoryDao poetryCategoryDao) {
            this.poetryCategoryDao = poetryCategoryDao;
        }

        @Override
        public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
            return poetryCategoryDao.findById(id, projectionType);
        }

        @Override
        public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
            return poetryCategoryDao.findByCriteria(criteria, pageable, projectionType);
        }

        @Override
        public int create(PoetryCategory poetryCategory) {
            return poetryCategoryDao.insert(poetryCategory);
        }

        @Override
        public int update(PoetryCategory poetryCategory) {
            return poetryCategoryDao.update(poetryCategory);
        }

        @Override
        public int deleteById(BigInteger id) {
            return poetryCategoryDao.deleteById(id);
        }
}