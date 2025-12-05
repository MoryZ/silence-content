package com.old.silence.content.domain.repository.poetry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import com.old.silence.content.domain.model.poetry.PoetryGrade;

import java.math.BigInteger;
import java.util.Optional;

/**
* PoetryGrade仓储接口
*/
public interface PoetryGradeRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(PoetryGrade poetryGrade);

    int update(PoetryGrade poetryGrade);

    int deleteById(BigInteger id);
}