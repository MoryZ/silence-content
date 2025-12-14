package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;


import java.math.BigInteger;
import java.util.Optional;

import com.old.silence.content.domain.model.codegen.FreemarkerTemplates;

/**
 * FreemarkerTemplates仓储接口
 */
public interface FreemarkerTemplatesRepository {

    <T> Optional<T> findByTemplateName(String templateName, Class<T> projectionType);

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(FreemarkerTemplates freemarkerTemplates);

    int update(FreemarkerTemplates freemarkerTemplates);

    int deleteById(BigInteger id);

}