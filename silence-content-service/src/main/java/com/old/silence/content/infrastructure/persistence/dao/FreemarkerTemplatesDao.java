package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.codegen.FreemarkerTemplates;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.Optional;

/**
 * FreemarkerTemplates数据访问接口
 */
public interface FreemarkerTemplatesDao extends JdbcRepository<FreemarkerTemplates, BigInteger> {

    <T> Optional<T> findByTemplateName(String templateName, Class<T> projectionType);

}