package com.old.silence.content.domain.repository.poetry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.enums.PromptTemplateType;
import com.old.silence.content.domain.model.poetry.PromptTemplate;

import java.math.BigInteger;
import java.util.Optional;

/**
 * PromptTemplate仓储接口
 */
public interface PromptTemplateRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Optional<T> findBySubCategoryIdAndTemplateType(BigInteger subCategoryId, PromptTemplateType templateType, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(PromptTemplate promptTemplate);

    int update(PromptTemplate promptTemplate);

    int deleteById(BigInteger id);
}