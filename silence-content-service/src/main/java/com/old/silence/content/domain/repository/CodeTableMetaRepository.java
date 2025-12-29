package com.old.silence.content.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import com.old.silence.content.domain.model.CodeApiDocument;
import com.old.silence.content.domain.model.CodeTableMeta;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * CodeTableMeta仓储接口
 */
public interface CodeTableMetaRepository {

    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);

    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);

    int create(CodeTableMeta codeTableMeta);

    int bulkCreate(List<CodeTableMeta> codeTableMetas);

    int bulkReplace(String schemaName, String tableName, List<CodeTableMeta> codeTableMetas);

    int deleteById(BigInteger id);
}