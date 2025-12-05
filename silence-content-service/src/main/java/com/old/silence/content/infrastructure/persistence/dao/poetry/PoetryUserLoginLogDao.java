package com.old.silence.content.infrastructure.persistence.dao;

import com.old.silence.content.domain.model.PoetryUserLoginLog;
import com.old.silence.data.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
* PoetryUserLoginLog数据访问接口
*/
public interface PoetryUserLoginLogDao extends JdbcRepository<PoetryUserLoginLog, BigInteger> {

}