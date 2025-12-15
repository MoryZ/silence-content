package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;


import java.math.BigInteger;
import java.util.Optional;

import com.old.silence.content.domain.model.codegen.CodeFileSpec;
import com.old.silence.content.domain.repository.CodeFileSpecRepository;
import com.old.silence.content.infrastructure.persistence.dao.CodeFileSpecDao;

/**
* CodeFileSpec仓储实现
*/
@Repository
public class CodeFileSpecMyBatisRepository implements CodeFileSpecRepository {
    private final CodeFileSpecDao codeFileSpecDao;

    public CodeFileSpecMyBatisRepository(CodeFileSpecDao codeFileSpecDao) {
        this.codeFileSpecDao = codeFileSpecDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return codeFileSpecDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return codeFileSpecDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(CodeFileSpec codeFileSpec) {
        return codeFileSpecDao.insert(codeFileSpec);
    }

    @Override
    public int update(CodeFileSpec codeFileSpec) {
        return codeFileSpecDao.update(codeFileSpec);
    }

    @Override
    public int deleteById(BigInteger id) {
        return codeFileSpecDao.deleteById(id);
    }
}