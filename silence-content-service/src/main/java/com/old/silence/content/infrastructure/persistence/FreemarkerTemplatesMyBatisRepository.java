package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.codegen.FreemarkerTemplates;
import com.old.silence.content.domain.repository.FreemarkerTemplatesRepository;
import com.old.silence.content.infrastructure.persistence.dao.FreemarkerTemplatesDao;


/**
 * FreemarkerTemplates仓储实现
 */
@Repository
public class FreemarkerTemplatesMyBatisRepository implements FreemarkerTemplatesRepository {
    private final FreemarkerTemplatesDao freemarkerTemplatesDao;

    public FreemarkerTemplatesMyBatisRepository(FreemarkerTemplatesDao freemarkerTemplatesDao) {
        this.freemarkerTemplatesDao = freemarkerTemplatesDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return freemarkerTemplatesDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return freemarkerTemplatesDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(FreemarkerTemplates freemarkerTemplates) {
        return freemarkerTemplatesDao.insert(freemarkerTemplates);
    }

    @Override
    public int update(FreemarkerTemplates freemarkerTemplates) {
        return freemarkerTemplatesDao.update(freemarkerTemplates);
    }

    @Override
    public int deleteById(BigInteger id) {
        return freemarkerTemplatesDao.deleteById(id);
    }
}