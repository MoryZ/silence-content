package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.codegen.CodeGenDatabase;
import com.old.silence.content.domain.repository.CodeGenDatabaseRepository;
import com.old.silence.content.infrastructure.persistence.dao.CodeGenDatabaseDao;

import java.math.BigInteger;
import java.util.Optional;


@Repository
public class CodeGenDatabaseMyBatisRepository implements CodeGenDatabaseRepository {

    private final CodeGenDatabaseDao codeGenDatabaseDao;

    public CodeGenDatabaseMyBatisRepository(CodeGenDatabaseDao codeGenDatabaseDao) {
        this.codeGenDatabaseDao = codeGenDatabaseDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return codeGenDatabaseDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return codeGenDatabaseDao.findByCriteria(criteria, pageable, projectionType);
    }


    @Override
    public int create(CodeGenDatabase food) {
        return codeGenDatabaseDao.insert(food);
    }

    @Override
    public int update(CodeGenDatabase food) {
        return codeGenDatabaseDao.update(food);
    }

    @Override
    public int deleteById(BigInteger id) {
        return codeGenDatabaseDao.deleteById(id);
    }
}
