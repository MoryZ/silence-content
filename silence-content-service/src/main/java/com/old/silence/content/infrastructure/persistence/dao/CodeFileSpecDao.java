package com.old.silence.content.infrastructure.persistence.dao;

import java.math.BigInteger;

import com.old.silence.content.domain.model.codegen.CodeFileSpec;
import com.old.silence.data.jdbc.repository.JdbcRepository;


/**
* CodeFileSpec数据访问接口
*/
public interface CodeFileSpecDao extends JdbcRepository<CodeFileSpec, BigInteger> {

}