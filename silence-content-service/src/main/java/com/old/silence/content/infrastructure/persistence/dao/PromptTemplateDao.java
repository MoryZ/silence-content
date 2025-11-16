package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.enums.PromptTemplateType;
import com.old.silence.content.domain.model.PromptTemplate;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import javax.swing.text.html.Option;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
* PromptTemplate数据访问接口
*/
public interface PromptTemplateDao extends JdbcRepository<PromptTemplate, BigInteger> {

    <T> Optional<T> findBySubCategoryIdAndTemplateType(BigInteger subCategoryId, PromptTemplateType templateType, Class<T> projectionType);
}