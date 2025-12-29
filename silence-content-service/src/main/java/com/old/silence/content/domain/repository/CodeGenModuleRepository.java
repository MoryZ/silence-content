package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.codegen.CodeGenModule;

import java.math.BigInteger;
import java.util.Optional;


public interface CodeGenModuleRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(CodeGenModule codeGenModule);

    int updateEnabledById(boolean enabled, BigInteger id);

    int update(CodeGenModule codeGenModule);

    int deleteById(BigInteger id);

}
