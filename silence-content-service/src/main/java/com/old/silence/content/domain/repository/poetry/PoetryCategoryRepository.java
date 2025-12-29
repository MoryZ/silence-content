package com.old.silence.content.domain.repository.poetry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.poetry.PoetryCategory;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * PoetryCategory仓储接口
 */
public interface PoetryCategoryRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> List<T> findByIds(List<BigInteger> ids, Class<T> projectionType);

    <T> List<T> findByParentId(BigInteger parentId, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(PoetryCategory poetryCategory);

    int update(PoetryCategory poetryCategory);

    int updateEnabledById(Boolean enabled, BigInteger id);

    int deleteById(BigInteger id);

}