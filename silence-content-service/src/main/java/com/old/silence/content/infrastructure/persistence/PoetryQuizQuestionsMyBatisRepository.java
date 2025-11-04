package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.model.PoetryQuizQuestions;
import com.old.silence.content.domain.repository.PoetryQuizQuestionsRepository;
import com.old.silence.content.infrastructure.persistence.dao.PoetryQuizQuestionsDao;

import java.math.BigInteger;
import java.util.Optional;

/**
* PoetryQuizQuestions仓储实现
*/
@Repository
public class PoetryQuizQuestionsMyBatisRepository implements PoetryQuizQuestionsRepository {
    private final PoetryQuizQuestionsDao poetryQuizQuestionsDao;

    public PoetryQuizQuestionsMyBatisRepository(PoetryQuizQuestionsDao poetryQuizQuestionsDao) {
        this.poetryQuizQuestionsDao = poetryQuizQuestionsDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryQuizQuestionsDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return poetryQuizQuestionsDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(PoetryQuizQuestions poetryQuizQuestions) {
        return poetryQuizQuestionsDao.insert(poetryQuizQuestions);
    }

    @Override
    public int update(PoetryQuizQuestions poetryQuizQuestions) {
        return poetryQuizQuestionsDao.update(poetryQuizQuestions);
    }

    @Override
    public int deleteById(BigInteger id) {
        return poetryQuizQuestionsDao.deleteById(id);
    }
}