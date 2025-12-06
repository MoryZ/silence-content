package com.old.silence.content.domain.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.codegen.CodeGenProjectModule;


public interface CodeGenProjectModuleRepository {
    
    <T> List<T> findByProjectId(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(CodeGenProjectModule codeGenProjectModule);

    int update(CodeGenProjectModule codeGenProjectModule);

    int bulkReplace(List<CodeGenProjectModule> codeGenProjectModules);

    int deleteById(BigInteger id);

}
