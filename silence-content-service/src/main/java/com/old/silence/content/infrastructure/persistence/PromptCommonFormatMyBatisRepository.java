package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.model.PromptCommonFormat;
import com.old.silence.content.domain.repository.PromptCommonFormatRepository;
import com.old.silence.content.infrastructure.persistence.dao.PromptCommonFormatDao;

import java.math.BigInteger;
import java.util.Optional;

/**
* PromptCommonFormat仓储实现
*/
@Repository
public class PromptCommonFormatMyBatisRepository implements PromptCommonFormatRepository {
    private final PromptCommonFormatDao promptCommonFormatDao;

    public PromptCommonFormatMyBatisRepository(PromptCommonFormatDao promptCommonFormatDao) {
        this.promptCommonFormatDao = promptCommonFormatDao;
    }

    @Override
    public <T> Optional<T> findByActive(Boolean active, Class<T> projectionType) {
        return promptCommonFormatDao.findByActive(active, projectionType);
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return promptCommonFormatDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return promptCommonFormatDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(PromptCommonFormat promptCommonFormat) {
        return promptCommonFormatDao.insert(promptCommonFormat);
    }

    @Override
    public int update(PromptCommonFormat promptCommonFormat) {
        return promptCommonFormatDao.update(promptCommonFormat);
    }

    @Override
    public int deleteById(BigInteger id) {
        return promptCommonFormatDao.deleteById(id);
    }
}