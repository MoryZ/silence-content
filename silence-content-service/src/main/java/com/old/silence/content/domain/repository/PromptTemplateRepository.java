package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import com.old.silence.content.domain.model.PromptTemplate;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
* PromptTemplate仓储接口
*/
public interface PromptTemplateRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> List<T> findBySubCategoryId(BigInteger subCategoryId, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(PromptTemplate promptTemplate);

    int update(PromptTemplate promptTemplate);

    int deleteById(BigInteger id);
}