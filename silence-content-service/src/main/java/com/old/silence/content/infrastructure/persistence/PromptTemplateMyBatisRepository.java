package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.enums.PromptTemplateType;
import com.old.silence.content.domain.model.PromptTemplate;
import com.old.silence.content.domain.repository.PromptTemplateRepository;
import com.old.silence.content.infrastructure.persistence.dao.PromptTemplateDao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
* PromptTemplate仓储实现
*/
@Repository
public class PromptTemplateMyBatisRepository implements PromptTemplateRepository {
    private final PromptTemplateDao promptTemplateDao;

    public PromptTemplateMyBatisRepository(PromptTemplateDao promptTemplateDao) {
        this.promptTemplateDao = promptTemplateDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return promptTemplateDao.findById(id, projectionType);
    }

    @Override
    public <T> Optional<T> findBySubCategoryIdAndTemplateType(BigInteger subCategoryId, PromptTemplateType templateType, Class<T> projectionType) {
        return promptTemplateDao.findBySubCategoryIdAndTemplateType(subCategoryId, templateType, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return promptTemplateDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(PromptTemplate promptTemplate) {
        return promptTemplateDao.insert(promptTemplate);
    }

    @Override
    public int update(PromptTemplate promptTemplate) {
        return promptTemplateDao.update(promptTemplate);
    }

    @Override
    public int deleteById(BigInteger id) {
        return promptTemplateDao.deleteById(id);
    }
}