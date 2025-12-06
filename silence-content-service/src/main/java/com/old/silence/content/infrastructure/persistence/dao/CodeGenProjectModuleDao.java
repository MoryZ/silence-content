package com.old.silence.content.infrastructure.persistence.dao;

import java.math.BigInteger;
import java.util.List;

import com.old.silence.content.domain.model.codegen.CodeGenProjectModule;
import com.old.silence.data.jdbc.repository.JdbcRepository;

/**
 * @author moryzang
 */

public interface CodeGenProjectModuleDao extends JdbcRepository<CodeGenProjectModule, BigInteger> {


    <T> List<T> findByProjectId(BigInteger id, Class<T> projectionType);

    void deleteByProjectId(BigInteger projectId);
}