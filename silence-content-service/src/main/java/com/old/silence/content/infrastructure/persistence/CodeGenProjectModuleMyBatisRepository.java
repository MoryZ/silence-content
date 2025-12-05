package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.codegen.CodeGenProjectModule;
import com.old.silence.content.domain.repository.CodeGenProjectModuleRepository;
import com.old.silence.content.infrastructure.persistence.dao.CodeGenProjectModuleDao;


@Repository
public class CodeGenProjectModuleMyBatisRepository implements CodeGenProjectModuleRepository {

    private final CodeGenProjectModuleDao codeGenProjectModuleDao;

    public CodeGenProjectModuleMyBatisRepository(CodeGenProjectModuleDao codeGenProjectModuleDao) {
        this.codeGenProjectModuleDao = codeGenProjectModuleDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return codeGenProjectModuleDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return codeGenProjectModuleDao.findByCriteria(criteria, pageable, projectionType);
    }
   

    @Override
    public int create(CodeGenProjectModule food) {
        return codeGenProjectModuleDao.insert(food);
    }

    @Override
    public int update(CodeGenProjectModule food) {
        return codeGenProjectModuleDao.update(food);
    }

    @Override
    public int deleteById(BigInteger id) {
        return codeGenProjectModuleDao.deleteById(id);
    }
}
