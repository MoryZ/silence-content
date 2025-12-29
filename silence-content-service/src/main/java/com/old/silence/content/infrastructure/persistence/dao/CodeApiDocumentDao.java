package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.CodeApiDocument;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;


/**
 * CodeApiDocument数据访问接口
 */
public interface CodeApiDocumentDao extends JdbcRepository<CodeApiDocument, BigInteger> {

    int deleteByTableName(String tableName);
}