package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.PromptTemplate;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;
import java.util.List;

/**
* PromptTemplate数据访问接口
*/
public interface PromptTemplateDao extends JdbcRepository<PromptTemplate, BigInteger> {

    <T> List<T> findBySubCategoryId(BigInteger subCategoryId, Class<T> projectionType);
}