package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import com.old.silence.content.domain.model.CodeApiDocument;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
* CodeApiDocument仓储接口
*/
public interface CodeApiDocumentRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(CodeApiDocument codeApiDocument);

    int bulkCreate(List<CodeApiDocument> codeApiDocuments);

    int bulkReplace(String tableName,List<CodeApiDocument> codeApiDocuments);

    int deleteById(BigInteger id);

}