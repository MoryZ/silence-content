package com.old.silence.content.infrastructure.persistence.dao;

import java.math.BigInteger;

import com.old.silence.content.domain.model.codegen.CodeGenProject;
import com.old.silence.data.jdbc.repository.JdbcRepository;

/**
 * @author moryzang
 */

public interface CodeGenProjectDao extends JdbcRepository<CodeGenProject, BigInteger> {


}