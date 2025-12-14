package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.codegen.CodeFileTemplate;
import com.old.silence.content.domain.repository.CodeFileTemplateRepository;
import com.old.silence.content.infrastructure.persistence.dao.CodeFileTemplateDao;


/**
 * FreemarkerTemplates仓储实现
 */
@Repository
public class CodeFileTemplateMyBatisRepository implements CodeFileTemplateRepository {
    private final CodeFileTemplateDao codeFileTemplateDao;

    @Override
    public <T> Optional<T> findByTemplateName(String templateName, Class<T> projectionType) {
        return codeFileTemplateDao.findByTemplateName(templateName, projectionType);
    }

    public CodeFileTemplateMyBatisRepository(CodeFileTemplateDao codeFileTemplateDao) {
        this.codeFileTemplateDao = codeFileTemplateDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return codeFileTemplateDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return codeFileTemplateDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(CodeFileTemplate codeFileTemplate) {
        return codeFileTemplateDao.insert(codeFileTemplate);
    }

    @Override
    public int update(CodeFileTemplate codeFileTemplate) {
        return codeFileTemplateDao.update(codeFileTemplate);
    }

    @Override
    public int deleteById(BigInteger id) {
        return codeFileTemplateDao.deleteById(id);
    }
}