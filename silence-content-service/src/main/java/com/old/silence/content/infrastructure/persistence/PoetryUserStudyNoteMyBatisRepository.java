package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.model.PoetryUserStudyNote;
import com.old.silence.content.domain.repository.PoetryUserStudyNoteRepository;
import com.old.silence.content.infrastructure.persistence.dao.PoetryUserStudyNoteDao;

import java.math.BigInteger;
import java.util.Optional;

/**
* PoetryUserStudyNote仓储实现
*/
@Repository
public class PoetryUserStudyNoteMyBatisRepository implements PoetryUserStudyNoteRepository {
        private final PoetryUserStudyNoteDao poetryUserStudyNoteDao;

        public PoetryUserStudyNoteMyBatisRepository(PoetryUserStudyNoteDao poetryUserStudyNoteDao) {
            this.poetryUserStudyNoteDao = poetryUserStudyNoteDao;
        }

        @Override
        public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
            return poetryUserStudyNoteDao.findById(id, projectionType);
        }

        @Override
        public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
            return poetryUserStudyNoteDao.findByCriteria(criteria, pageable, projectionType);
        }

        @Override
        public int create(PoetryUserStudyNote poetryUserStudyNote) {
            return poetryUserStudyNoteDao.insert(poetryUserStudyNote);
        }

        @Override
        public int update(PoetryUserStudyNote poetryUserStudyNote) {
            return poetryUserStudyNoteDao.update(poetryUserStudyNote);
        }

        @Override
        public int deleteById(BigInteger id) {
            return poetryUserStudyNoteDao.deleteById(id);
        }
}