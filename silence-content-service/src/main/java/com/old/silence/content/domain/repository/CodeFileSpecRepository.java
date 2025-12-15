package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;


import java.math.BigInteger;
import java.util.Optional;

import com.old.silence.content.domain.model.codegen.CodeFileSpec;

/**
* CodeFileSpec仓储接口
*/
public interface CodeFileSpecRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(CodeFileSpec codeFileSpec);

    int update(CodeFileSpec codeFileSpec);

    int deleteById(BigInteger id);
}