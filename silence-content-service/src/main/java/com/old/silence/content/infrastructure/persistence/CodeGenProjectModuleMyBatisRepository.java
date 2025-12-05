package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.codegen.CodeGenModule;
import com.old.silence.content.domain.repository.CodeGenModuleRepository;
import com.old.silence.content.infrastructure.persistence.dao.CodeGenModuleDao;


@Repository
public class CodeGenModuleMyBatisRepository implements CodeGenModuleRepository {

    private final CodeGenModuleDao codeGenModuleDao;

    public CodeGenModuleMyBatisRepository(CodeGenModuleDao codeGenModuleDao) {
        this.codeGenModuleDao = codeGenModuleDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return codeGenModuleDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return codeGenModuleDao.findByCriteria(criteria, pageable, projectionType);
    }
   

    @Override
    public int create(CodeGenModule food) {
        return codeGenModuleDao.insert(food);
    }

    @Override
    public int update(CodeGenModule food) {
        return codeGenModuleDao.update(food);
    }

    @Override
    public int deleteById(BigInteger id) {
        return codeGenModuleDao.deleteById(id);
    }
}
