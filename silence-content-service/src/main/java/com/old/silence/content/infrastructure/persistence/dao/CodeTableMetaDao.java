package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.CodeTableMeta;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;


/**
 * CodeTableMeta数据访问接口
 */
public interface CodeTableMetaDao extends JdbcRepository<CodeTableMeta, BigInteger> {

    int deleteBySchemaNameAndTableName(String schemaName, String tableName);
}