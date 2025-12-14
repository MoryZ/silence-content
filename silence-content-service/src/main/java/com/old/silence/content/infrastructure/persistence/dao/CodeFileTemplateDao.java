package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.codegen.CodeFileTemplate;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.Optional;

/**
 * CodeFileTemplates数据访问接口
 */
public interface CodeFileTemplateDao extends JdbcRepository<CodeFileTemplate, BigInteger> {

    <T> Optional<T> findByTemplateName(String templateName, Class<T> projectionType);

}