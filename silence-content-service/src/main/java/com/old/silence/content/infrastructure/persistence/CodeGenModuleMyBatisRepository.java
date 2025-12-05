package com.old.silence.content.infrastructure.persistence;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.codegen.CodeGenProject;
import com.old.silence.content.domain.repository.CodeGenProjectRepository;
import com.old.silence.content.infrastructure.persistence.dao.CodeGenProjectDao;


@Repository
public class CodeGenProjectMyBatisRepository implements CodeGenProjectRepository {

    private final CodeGenProjectDao codeGenProjectDao;

    public CodeGenProjectMyBatisRepository(CodeGenProjectDao codeGenProjectDao) {
        this.codeGenProjectDao = codeGenProjectDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return codeGenProjectDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return codeGenProjectDao.findByCriteria(criteria, pageable, projectionType);
    }
   

    @Override
    public int create(CodeGenProject food) {
        return codeGenProjectDao.insert(food);
    }

    @Override
    public int update(CodeGenProject food) {
        return codeGenProjectDao.update(food);
    }

    @Override
    public int deleteById(BigInteger id) {
        return codeGenProjectDao.deleteById(id);
    }
}
