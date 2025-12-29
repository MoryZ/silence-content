package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.codegen.CodeGenProject;

import java.math.BigInteger;
import java.util.Optional;


public interface CodeGenProjectRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(CodeGenProject codeGenProject);

    int update(CodeGenProject codeGenProject);

    int deleteById(BigInteger id);


}
