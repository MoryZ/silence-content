package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.codegen.CodeGenModule;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * @author moryzang
 */

public interface CodeGenModuleDao extends JdbcRepository<CodeGenModule, BigInteger> {


    int updateEnabled(Boolean enabled, BigInteger id);
}