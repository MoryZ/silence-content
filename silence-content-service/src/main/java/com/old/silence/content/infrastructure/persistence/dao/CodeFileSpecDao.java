package com.old.silence.content.infrastructure.persistence.dao;


import com.old.silence.content.domain.model.codegen.CodeFileSpec;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
 * 代码文件规格数据访问对象接口
 * 基于自定义 JdbcRepository 的 DAO 定义，提供代码文件规格的数据库操作方法
 *
 * @author moryzang
 */
public interface CodeFileSpecDao extends JdbcRepository<CodeFileSpec, BigInteger> {

}
