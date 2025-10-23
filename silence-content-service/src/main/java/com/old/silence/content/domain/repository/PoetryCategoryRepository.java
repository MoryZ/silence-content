package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import com.old.silence.content.domain.model.PoetryCategory;

import java.math.BigInteger;
import java.util.Optional;

/**
* PoetryCategory仓储接口
*/
public interface PoetryCategoryRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(PoetryCategory poetryCategory);

    int update(PoetryCategory poetryCategory);

    int deleteById(BigInteger id);
}