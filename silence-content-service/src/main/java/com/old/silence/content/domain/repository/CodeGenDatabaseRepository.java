package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.codegen.CodeGenDatabase;

import java.math.BigInteger;
import java.util.Optional;


public interface CodeGenDatabaseRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(CodeGenDatabase codeGenDatabase);

    int update(CodeGenDatabase codeGenDatabase);

    int deleteById(BigInteger id);


}
