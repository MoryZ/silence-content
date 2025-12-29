package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.codegen.CodeFileTemplate;

import java.math.BigInteger;
import java.util.Optional;

/**
 * CodeFileTemplate仓储接口
 */
public interface CodeFileTemplateRepository {

    <T> Optional<T> findByTemplateName(String templateName, Class<T> projectionType);

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(CodeFileTemplate codeFileTemplate);

    int update(CodeFileTemplate codeFileTemplate);

    int deleteById(BigInteger id);

}