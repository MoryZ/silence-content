package com.old.silence.content.infrastructure.persistence.dao.poetry;

import com.old.silence.content.domain.enums.PromptTemplateType;
import com.old.silence.content.domain.model.poetry.PromptTemplate;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.Optional;

/**
 * PromptTemplate数据访问接口
 */
public interface PromptTemplateDao extends JdbcRepository<PromptTemplate, BigInteger> {

    <T> Optional<T> findBySubCategoryIdAndTemplateType(BigInteger subCategoryId, PromptTemplateType templateType, Class<T> projectionType);
}