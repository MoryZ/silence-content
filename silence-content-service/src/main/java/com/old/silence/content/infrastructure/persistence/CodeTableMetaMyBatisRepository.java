package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.old.silence.content.domain.model.CodeTableMeta;
import com.old.silence.content.domain.repository.CodeTableMetaRepository;
import com.old.silence.content.infrastructure.persistence.dao.CodeTableMetaDao;

import java.math.BigInteger;
import java.util.Optional;

/**
* CodeTableMeta仓储实现
*/
@Repository
public class CodeTableMetaMyBatisRepository implements CodeTableMetaRepository {
    private final CodeTableMetaDao codeTableMetaDao;

    public CodeTableMetaMyBatisRepository(CodeTableMetaDao codeTableMetaDao) {
        this.codeTableMetaDao = codeTableMetaDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return codeTableMetaDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return codeTableMetaDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(CodeTableMeta codeTableMeta) {
        return codeTableMetaDao.insert(codeTableMeta);
    }

    @Override
    public int update(CodeTableMeta codeTableMeta) {
        return codeTableMetaDao.update(codeTableMeta);
    }

    @Override
    public int deleteById(BigInteger id) {
        return codeTableMetaDao.deleteById(id);
    }
}