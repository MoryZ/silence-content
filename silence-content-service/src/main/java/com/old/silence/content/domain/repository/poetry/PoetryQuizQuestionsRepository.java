package com.old.silence.content.domain.repository.poetry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.poetry.PoetryQuizQuestions;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * PoetryQuizQuestions仓储接口
 */
public interface PoetryQuizQuestionsRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int bulkCreate(List<PoetryQuizQuestions> poetryQuizQuestions);

    int update(PoetryQuizQuestions poetryQuizQuestions);

    int deleteById(BigInteger id);
}